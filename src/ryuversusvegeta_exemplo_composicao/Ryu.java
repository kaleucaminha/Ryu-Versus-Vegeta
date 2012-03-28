package ryuversusvegeta_exemplo_composicao;

import javaPlayExtras.ObjetoComGravidade;
import javaPlay.Imagem;
import java.awt.Color;
import java.awt.Graphics;
import javaPlay.GameEngine;
import javaPlay.Keyboard;
import javaPlay.Keys;
import javax.swing.JOptionPane;

public class Ryu extends ObjetoComGravidade {

    protected Imagem imgNormal;
    protected Imagem imgTras;
    protected Imagem imgFrente;
    protected Imagem imgPulo;

    //Esta � a imagem que sempre ser� desenhada e utilizada para colis�o
    //Ela � uma refer�ncia para uma das imagens acima.
    protected Imagem imgAtual;

    protected int velocidade = 10;
    protected int vida = 100;
    
    public Ryu() {
        try {
            //COMPOSI��O TIPO 1
            //A classe Ryu � composta por diversos objetos da classe Imagem
            //E a inicializa��o das partes � feita pelo construtor da classe.
            this.imgNormal = new Imagem("resources/ryu/normal.png");
            this.imgTras = new Imagem("resources/ryu/normal.png");
            this.imgFrente = new Imagem("resources/ryu/normal.png");
            this.imgPulo = new Imagem("resources/ryu/pulo.png");
           
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Exce��o: "+ex.getMessage());
            System.exit(0);
        }

        this.imgAtual = this.imgNormal;
        this.x = 400;
        
        //Essencial par o objetoComGravidade poder trabalhar
        this.altura = this.imgAtual.pegaAltura();
        this.largura = this.imgAtual.pegaLargura();
    }

    public void step(long timeElapsed) {
        //Executa step da classe ObjetoComMovimento
        super.step(timeElapsed);
        
        Keyboard keyboard = GameEngine.getInstance().getKeyboard();

        if (keyboard.keyDown(Keys.ESPACO)) {
          this.pula();
        }
        
        if( keyboard.keyDown(Keys.ESQUERDA) ){
          this.frente();
        } else if( keyboard.keyDown(Keys.DIREITA) ){
          this.tras();
        } else if( keyboard.keyDown(Keys.CIMA) ){
          this.y -= this.velocidade;
        } else if( keyboard.keyDown(Keys.BAIXO) ){
          this.y += this.velocidade;
        } else {
          this.normal();
        }                
        
    }


    public void alteraImagem(Imagem novaImagem){
        //this.imgAtual = novaImagem;
        this.setAltura( this.imgAtual.pegaAltura() );
        this.setLargura( this.imgAtual.pegaLargura() );
    }

    public void normal() {
        if(this.estaSubindo() || this.estaDescendo()){
            this.alteraImagem(this.imgPulo);
        } else {
            this.alteraImagem( this.imgNormal );
        }
        
    }

    public void pula() {
        if(this.estaNoChao){
            this.alteraImagem(this.imgPulo);
            this.impulso( 35 );
        }        

    }

    public void frente() {
        this.alteraImagem(this.imgFrente);
        this.x -= this.velocidade;
    }

    public void tras() {
        this.alteraImagem(this.imgTras);
        this.x += this.velocidade;
    }

    public void perdeVida(int pontos){
        this.vida -= pontos;
    }

    public boolean estaMorto(){
        return (this.vida <= 0);
    }
   

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.drawString(this.vida + "", this.x + 20, this.y - 10);

        g.drawRect(this.x, this.y, this.imgAtual.pegaLargura(), this.imgAtual.pegaAltura());
        
        this.imgAtual.drawFlipped(g, this.x, this.y);
    }
}

