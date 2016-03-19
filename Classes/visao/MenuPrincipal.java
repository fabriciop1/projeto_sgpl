/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import controle.ControleLogin;
import controle.ControlePerfil;
import modelo.negocio.Perfil;
import modelo.negocio.Usuario;

/**
 *
 * @author Alexandre
 */
public class MenuPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        
        initComponents();
        
        setLocationRelativeTo(null);
        this.setResizable(false);
        
        Perfil atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        
        nomProd.setText(atual.getNome());
        tamProp.setText("" + atual.getTamPropriedade());
        nomCida.setText(atual.getCidade());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textoEntrada = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nomProd = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tamProp = new javax.swing.JLabel();
        nomCida = new javax.swing.JLabel();
        btnAnalises = new javax.swing.JButton();
        btnPerfil = new javax.swing.JButton();
        btnDadosTecnMensais = new javax.swing.JButton();
        btnIndicTecnMensais = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();
        btnIndicEconMensais = new javax.swing.JButton();
        btnIndicTecnAnuais = new javax.swing.JButton();
        btnDadosEconMensais = new javax.swing.JButton();
        btnIndicEconAnuais = new javax.swing.JButton();
        btnRetornoMensal = new javax.swing.JButton();
        btnCustoImplForra = new javax.swing.JButton();
        btnCalculoRacao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textoEntrada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        textoEntrada.setForeground(new java.awt.Color(51, 153, 255));
        textoEntrada.setText("Grupo de Estudo em Gestão Rural Pecuária Leiteira");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Produtor:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Tamanho:");

        nomProd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nomProd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomProd.setText("nome_produtor");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Cidade:");

        tamProp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tamProp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tamProp.setText("tamanho_propriedade");

        nomCida.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nomCida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomCida.setText("nome_cidade");

        btnAnalises.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAnalises.setText("Análises");
        btnAnalises.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalisesActionPerformed(evt);
            }
        });

        btnPerfil.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPerfil.setText("Visualizar Perfil(s)");
        btnPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerfilActionPerformed(evt);
            }
        });

        btnDadosTecnMensais.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDadosTecnMensais.setText("Dados Técnicos Mensais");
        btnDadosTecnMensais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDadosTecnMensaisActionPerformed(evt);
            }
        });

        btnIndicTecnMensais.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnIndicTecnMensais.setText("Indicadores Técnicos Mensais");
        btnIndicTecnMensais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndicTecnMensaisActionPerformed(evt);
            }
        });

        btnInventario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnInventario.setText("Inventário");
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });

        btnIndicEconMensais.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnIndicEconMensais.setText("Indicadores Econômicos Mensais");
        btnIndicEconMensais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndicEconMensaisActionPerformed(evt);
            }
        });

        btnIndicTecnAnuais.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnIndicTecnAnuais.setText("Indicadores Técnicos Anuais");
        btnIndicTecnAnuais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndicTecnAnuaisActionPerformed(evt);
            }
        });

        btnDadosEconMensais.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDadosEconMensais.setText("Dados Econômicos Mensais");
        btnDadosEconMensais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDadosEconMensaisActionPerformed(evt);
            }
        });

        btnIndicEconAnuais.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnIndicEconAnuais.setText("Indicadores Econômicos Anuais");
        btnIndicEconAnuais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndicEconAnuaisActionPerformed(evt);
            }
        });

        btnRetornoMensal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnRetornoMensal.setText("Retorno Mensal");
        btnRetornoMensal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetornoMensalActionPerformed(evt);
            }
        });

        btnCustoImplForra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCustoImplForra.setText("Custo de Implantação de Forrageiras");
        btnCustoImplForra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustoImplForraActionPerformed(evt);
            }
        });

        btnCalculoRacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCalculoRacao.setText("Cálculo de Ração");
        btnCalculoRacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculoRacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(290, 290, 290)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textoEntrada)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(36, 36, 36)
                            .addComponent(nomCida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(nomProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tamProp, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(92, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDadosTecnMensais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAnalises, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCalculoRacao, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(btnPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRetornoMensal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIndicEconMensais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIndicTecnMensais, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCustoImplForra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIndicEconAnuais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDadosEconMensais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIndicTecnAnuais, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(textoEntrada)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nomProd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tamProp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(nomCida))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDadosTecnMensais, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAnalises, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnIndicEconMensais, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnIndicTecnMensais, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDadosEconMensais, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnIndicEconAnuais, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnIndicTecnAnuais, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRetornoMensal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustoImplForra, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCalculoRacao, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(121, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnalisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalisesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnalisesActionPerformed

    private void btnPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerfilActionPerformed
        new VisualizarPerfil().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnPerfilActionPerformed

    private void btnDadosTecnMensaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDadosTecnMensaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDadosTecnMensaisActionPerformed

    private void btnIndicTecnMensaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndicTecnMensaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIndicTecnMensaisActionPerformed

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        new VisualizarInventario().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnIndicEconMensaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndicEconMensaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIndicEconMensaisActionPerformed

    private void btnIndicTecnAnuaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndicTecnAnuaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIndicTecnAnuaisActionPerformed

    private void btnDadosEconMensaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDadosEconMensaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDadosEconMensaisActionPerformed

    private void btnIndicEconAnuaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndicEconAnuaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIndicEconAnuaisActionPerformed

    private void btnRetornoMensalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetornoMensalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRetornoMensalActionPerformed

    private void btnCustoImplForraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustoImplForraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCustoImplForraActionPerformed

    private void btnCalculoRacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculoRacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCalculoRacaoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
        
        
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalises;
    private javax.swing.JButton btnCalculoRacao;
    private javax.swing.JButton btnCustoImplForra;
    private javax.swing.JButton btnDadosEconMensais;
    private javax.swing.JButton btnDadosTecnMensais;
    private javax.swing.JButton btnIndicEconAnuais;
    private javax.swing.JButton btnIndicEconMensais;
    private javax.swing.JButton btnIndicTecnAnuais;
    private javax.swing.JButton btnIndicTecnMensais;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnPerfil;
    private javax.swing.JButton btnRetornoMensal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel nomCida;
    private javax.swing.JLabel nomProd;
    private javax.swing.JLabel tamProp;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
