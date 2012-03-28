package ryuversusvegeta_exemplo_composicao;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import javaPlay.GameStateController;
import javaPlayExtras.CenarioComColisao;
import javax.swing.JOptionPane;

public class Fase2 implements GameStateController{

  private CenarioComColisao cenario;
  private ArrayList<Inimigo> inimigos;
  private Ryu ryu;

  private long contadorTempo;

  public Fase2(Ryu personagem){
     //Composi��o tipo 3
     //A fase � composta, dentre outras coisas,
     //Por um objeto da classe Ryu
     //Este objeto vem de fora da classe.
     this.ryu = personagem;
  }

  public void load() {     
         
     this.inimigos = new ArrayList<Inimigo>();

     try {
        this.cenario = new CenarioComColisao("resources/cenario2.scn");
        this.cenario.adicionaObjeto(ryu); //Aqui, o controle de colis�o �� transferido para o cenario
    } catch (FileNotFoundException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
     
  }

  public void step(long timeElapsed) {
      if(this.ryu.estaMorto()){
         JOptionPane.showMessageDialog(null, "Perdeu.");
         System.exit(0);
     }

     this.ryu.step(timeElapsed);
     for(Inimigo inimigo : this.inimigos){
         if(this.ryu.temColisao( inimigo )){
             this.ryu.perdeVida(10);
             this.ryu.setX(200);
         }         
         inimigo.step(timeElapsed);         
     }     

     if(this.cenario.temColisaoComTile(ryu, 4)){
         JOptionPane.showMessageDialog(null, "Parab�ns, voc� venceu.");
         System.exit(0);
     }
    
     this.cenario.step(timeElapsed);

     this.contadorTempo += timeElapsed;
     if(this.contadorTempo > 3000){ //tres segundos
         Inimigo novo = new Inimigo(750, 50);
         novo.persegueObjeto(ryu);

         this.inimigos.add( novo );
         this.cenario.adicionaObjeto( novo );
         this.contadorTempo -= 3000;
     }
  }

  public void draw(Graphics g) {
      g.setColor(Color.CYAN);
      g.fillRect(0, 0, 800, 600);

      
      this.cenario.draw(g);
      this.ryu.draw(g);
      for(Inimigo inimigo : this.inimigos){
          inimigo.draw(g);
      }
  }

  public void unload() {}

  public void start() {
     //Inicia no canto eaquerdo do cen�rio
     this.ryu.setX(50);
  }

  public void stop() {}

}
