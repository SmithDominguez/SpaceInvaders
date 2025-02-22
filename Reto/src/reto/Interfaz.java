/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package reto;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author PC
 */
public class Interfaz extends javax.swing.JFrame {
    private List<JLabel> bullets;
    private List<JLabel> enemies;
    /**
     * Creates new form Interfaz
     */
    public Interfaz() {
        initComponents();
        bulletTest.setVisible(false);
        explosion.setVisible(false);
        win.setVisible(false);
        
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        
        createEnemy(enemies);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        player = new javax.swing.JLabel();
        explosion = new javax.swing.JLabel();
        bulletTest = new javax.swing.JPanel();
        presionarEspacio = new javax.swing.JLabel();
        win = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(null);

        player.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reto/player.png"))); // NOI18N
        getContentPane().add(player);
        player.setBounds(20, 430, 59, 80);

        explosion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reto/explosion.png"))); // NOI18N
        getContentPane().add(explosion);
        explosion.setBounds(870, 10, 20, 16);

        bulletTest.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout bulletTestLayout = new javax.swing.GroupLayout(bulletTest);
        bulletTest.setLayout(bulletTestLayout);
        bulletTestLayout.setHorizontalGroup(
            bulletTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bulletTestLayout.setVerticalGroup(
            bulletTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(bulletTest);
        bulletTest.setBounds(47, 410, 5, 20);

        presionarEspacio.setText("Presiona \"espacio\" para Disparar");
        getContentPane().add(presionarEspacio);
        presionarEspacio.setBounds(350, 290, 200, 30);

        win.setFont(new java.awt.Font("OCR A Extended", 0, 36)); // NOI18N
        win.setForeground(new java.awt.Color(0, 255, 51));
        win.setText("YOU WIN!");
        getContentPane().add(win);
        win.setBounds(330, 170, 200, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void createEnemy(List<JLabel> enemies) {
        Random rand = new Random();
        
        int panelWidth = 700;  
        int enemyPerRow = 10;
        int enemyWidth = 30;
        int enemyHeight = 30;
        ImageIcon icon = new ImageIcon(getClass().getResource("enemy.png"));  // Sin barra inclinada ("/")
        
        for (int i = 1; i <= 43; i++) {
            //Crear enemigo
            JLabel enemy = new JLabel("Enemy " + i);

            int row = (i - 1) / enemyPerRow;  
            int col = (i - 1) % enemyPerRow;  

            //Ubicar los enemigos
            int x = rand.nextInt(panelWidth / enemyPerRow) + col * (panelWidth / enemyPerRow);
            int y = row * (enemyHeight + 10);
            enemy.setBounds(x, y, enemyWidth, enemyHeight);
            enemy.setText("");
            enemy.setOpaque(false);
            enemy.setIcon(icon);

            add(enemy); 
            enemies.add(enemy);
        }
    }
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        int code = evt.getKeyCode();
        switch(code) {
            case KeyEvent.VK_LEFT -> {
                player.setBounds(player.getX()-5,player.getY(),player.getWidth(),player.getHeight());
                bulletTest.setBounds(bulletTest.getX()-5,bulletTest.getY(),bulletTest.getWidth(),bulletTest.getHeight());
            }
            case KeyEvent.VK_RIGHT -> {
                player.setBounds(player.getX()+5,player.getY(),player.getWidth(),player.getHeight());
                bulletTest.setBounds(bulletTest.getX()+5,bulletTest.getY(),bulletTest.getWidth(),bulletTest.getHeight()); 
            }
            case KeyEvent.VK_SPACE -> {
                shootBullet();
                presionarEspacio.setVisible(false);
            }
        }
    }//GEN-LAST:event_formKeyPressed
    public void shootBullet(){
        JLabel bullet = new JLabel("Bullet");
        bullet.setBounds(bulletTest.getX(), bulletTest.getY(), bulletTest.getWidth(), bulletTest.getHeight());
        bullet.setBackground(Color.RED);
        add(bullet);
        bullets.add(bullet); 

        new Thread(() -> {
            for (int i = player.getY(); i >= 0; i -= 5) {
                bullet.setLocation(bullet.getX(), i);
                
                if (checkCollision(bullet)) {
                    int cantidadDeEnemigos = enemies.size();
                    if (cantidadDeEnemigos==0){
                        win.setVisible(true);
                    }
                    break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.print(e);
                }
            }
            
            bullet.setVisible(false);
            remove(bullet);  
            bullets.remove(bullet); 
        }).start();
    }

    public boolean checkCollision(JLabel bullet) {
        for (JLabel enemy : enemies) {
            if (bullet.getBounds().intersects(enemy.getBounds())) {            
                enemy.setVisible(false);
                remove(enemy);
                enemies.remove(enemy);

                bullet.setVisible(false);
                remove(bullet);
                bullets.remove(bullet);
                
                explosion.setBounds(enemy.getX(),enemy.getY(), enemy.getWidth(),enemy.getHeight());
                explosion.setVisible(true);
                try {
                    Thread.sleep(50);
                    explosion.setVisible(false);
                } catch (InterruptedException e) {
                    System.out.print(e);
                }
                
                return true; 
            }
        }
        return false;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    } 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bulletTest;
    public javax.swing.JLabel explosion;
    public javax.swing.JLabel player;
    private javax.swing.JLabel presionarEspacio;
    private javax.swing.JLabel win;
    // End of variables declaration//GEN-END:variables
}
