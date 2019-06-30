package myrobots;

import robocode.*;
import java.awt.Color;

// link útil: https://www.gsigma.ufsc.br/~popov/aulas/robocode/

/**
 * Robot: Purgatory
 * @author Ivan Simplício
 */
public class Purgatory extends AdvancedRobot{

	private int qntAcertos = 0;
	private int qntErros = 0;
	private final int DISTANCIA_PAREDE = 50;
	private final int DESLOCAMENTO_PADRAO = 100;
	/**
	 * run: Comportamento padrão do Purgatory
	 */
	public void run() {
		
		//chassi, canhao, radar, projetil, arcoRadar
	    setColors(Color.red, Color.black, Color.black, Color.red, Color.red);

		// Loop principal do robô
		while(true) {
			deslocamentoPadrao();
		}
	}

	/**
	 * onScannedRobot: O que fazer quando detectar um adversario
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if(getEnergy() > 20 || e.getDistance() < 50){
			fire(3);
		}else if(e.getDistance() > 150){
			fire(1);
		}else{
			fire(2);
		}
	}

	/**
	 * onHitByBullet: O que fazer quando é atingido por um projétil
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(45);
		ahead(DESLOCAMENTO_PADRAO);
	}
	
	/**
	 * onHitRobot: O que fazer quando o robo colide com outro robo
	 */
	public void onHitRobot(HitRobotEvent e){
		if (e.getBearing() > -10 && e.getBearing() < 10) {
			fire(3);
		}
		if (e.isMyFault()) {
			turnRight(15);
		}
	}
	
	/**
	 * onHitWall: O que fazer quando colidir com uma parede
	 */
	public void onHitWall(HitWallEvent e) {
		distanciarDaParede();
	}
	
	/**
	 * onBulletHit: O que fazer quando atingir um adversario
	 */
	public void onBulletHit(BulletHitEvent e){
		qntAcertos++;
	}
	
	/**
	 * onBulletMissed: O que fazer quando o tiro errar o alvo (acerta a parede)
	 */
	public void onBulletMissed(BulletMissedEvent e){
		qntErros++;
	}
	
	/**
 	* onWin: O que fazer quando vencer
 	*/
	public void onWin(WinEvent e){
		comemoracao();
		System.out.println("\n"+getName()+" venceu!");
		exibeEstatisticas();	
	}
	
	/**
	 * onDeath: O que fazer quando morrer
	 */
	public void onDeath(DeathEvent e){
		System.out.println("\n"+getName()+" morreu!");
		exibeEstatisticas();
	}
	
	/**
	 * deslocamentoPadrao : Realiza o movimento padrao definido para o robo
	 */
	private void deslocamentoPadrao(){
		setTurnRight(360);
		setMaxVelocity(6);
		ahead(DESLOCAMENTO_PADRAO*1.5);
	}
	
	/**
	* distanciarDaParede : Faz com que o robo se distancie da parede.
	*/
	private void distanciarDaParede(){
		double arenaAltura = getBattleFieldHeight();
		double arenaLargura = getBattleFieldWidth();
		turnLeft(getHeading());
		if(getY() < DISTANCIA_PAREDE){
			ahead(arenaAltura/5);
		}else if(getY() > arenaAltura - DISTANCIA_PAREDE){
			back(arenaAltura/5);
		}
		if(getX() < DISTANCIA_PAREDE){
			turnRight(90);
			ahead(arenaLargura/5);
		}else if(getX() > arenaLargura - DISTANCIA_PAREDE){
			turnLeft(90);
			ahead(arenaLargura/5);
		}		
	}
	
	/**
	 * exibeEstatisticas: Exibe estatisticas totais de acertos e erros dos projeteis lancados a cada round.
	 */
	private void exibeEstatisticas(){
		System.out.println("Estatísticas: ");
		System.out.println("Total de projéteis lançados : "+(qntAcertos+qntErros));
		System.out.println("Total de projéteis acertados: "+qntAcertos);
		System.out.println("Total de projéteis errados  : "+qntErros);
	}
	
	/**
	* comemoracao : Realiza movimentos comemorativos a vitoria.
	*/
	private void comemoracao() {
		for (int i = 0; i < 50; i++) {
			turnRight(30);
			turnLeft(30);
		}
	}
}