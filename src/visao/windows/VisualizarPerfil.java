/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao.windows;

import controle.ControleLogin;
import controle.ControlePerfil;
import flex.db.GenericDAO;
import flex.table.GenericTableModifier;
import flex.table.GenericTableRowEditor;
import flex.table.TableModifiedEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.negocio.Perfil;
import modelo.negocio.Rota;
import modelo.negocio.Usuario;
import util.Cast;
import util.CellEditor;

/**
 *
 * @author Alexandre
 */
public class VisualizarPerfil extends javax.swing.JFrame {
    
    GenericDAO<Perfil> perfilDAO;
    GenericDAO<Rota> rotaDAO;
    List<Perfil> perfis;
    ArrayList<Integer> idPerfis;
    Usuario usuario;
    GenericTableRowEditor listaPerfisGTRE;
    
    
    /**
     * Creates new form VisualizarPerfil
     */
    public VisualizarPerfil() {
        
        initComponents();
        listaPerfis.getSelectionModel().clearSelection();
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        
        super.setTitle("SGPL - Perfis disponíveis");
        
        listaPerfis.setShowHorizontalLines(true);
      
        listaPerfis.setColumnSelectionAllowed(false);
        
        rotaDAO = new GenericDAO<>(Rota.class);
        perfilDAO = new GenericDAO<>(Perfil.class);
        
        usuario = ControleLogin.getInstance().getUsuario();
        
        idPerfis = new ArrayList<>();
        
        if (!usuario.getLogin().equals("adm")) { // Sabendo se o usuário é ou não o administrador pelo login
            perfis = perfilDAO.retrieveByColumn("idRotaFK", usuario.getRota());
        } else {
            perfis = perfilDAO.retrieveAll();
        }
        
        listaPerfisGTRE = new GenericTableRowEditor(this, listaPerfis, false);
        listaPerfisGTRE.getEditTable().setDefaultEditor(Object.class, new CellEditor());
        listaPerfisGTRE.getSourceTableModel().setRowCount(0);
        listaPerfisGTRE.setAllowEmptyCells(false);
        setRotasCombo();
         
        for(int i = 0; i < perfis.size(); i++){
            
            idPerfis.add(perfis.get(i).getId());
            
            listaPerfisGTRE.addSourceTableRow(new Object[]{
                perfis.get(i).getNome(),
                perfis.get(i).getCidade(),
                perfis.get(i).getRota().getRota(), 
                perfis.get(i).getTamPropriedade(),
                perfis.get(i).getAreaPecLeite(),
                perfis.get(i).getProdLeiteDiario(),
                perfis.get(i).getEmpPermanentes(),
                perfis.get(i).getNumFamiliares(),},
                perfis.get(i).getId());
        }
       
       verificaTabelaVazia();
       
       definirBDListeners();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        btnAcessar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaPerfis = new javax.swing.JTable();
        removerPerfilBT = new javax.swing.JButton();
        editPerfilBT = new javax.swing.JButton();
        addPerfilBT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 38, 255));
        jLabel1.setText("Perfis disponíveis");

        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnAcessar.setText("Acessar");
        btnAcessar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcessarActionPerformed(evt);
            }
        });

        listaPerfis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Cidade", "Rota", "Tam. Propriedade (ha)", "Área Pec. Leiteira (ha)", "Prod. Diária de Leite", "Empregados Permanentes", "Nº de Familiares"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listaPerfis.setColumnSelectionAllowed(true);
        listaPerfis.getTableHeader().setReorderingAllowed(false);
        listaPerfis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaPerfisMouseClicked(evt);
            }
        });
        listaPerfis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                listaPerfisKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(listaPerfis);
        listaPerfis.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (listaPerfis.getColumnModel().getColumnCount() > 0) {
            listaPerfis.getColumnModel().getColumn(0).setResizable(false);
            listaPerfis.getColumnModel().getColumn(1).setResizable(false);
            listaPerfis.getColumnModel().getColumn(2).setResizable(false);
            listaPerfis.getColumnModel().getColumn(3).setResizable(false);
            listaPerfis.getColumnModel().getColumn(4).setResizable(false);
            listaPerfis.getColumnModel().getColumn(5).setResizable(false);
            listaPerfis.getColumnModel().getColumn(6).setResizable(false);
            listaPerfis.getColumnModel().getColumn(7).setResizable(false);
        }

        removerPerfilBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/delete.png"))); // NOI18N
        removerPerfilBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerPerfilBTActionPerformed(evt);
            }
        });

        editPerfilBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit.png"))); // NOI18N
        editPerfilBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPerfilBTActionPerformed(evt);
            }
        });

        addPerfilBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/add.png"))); // NOI18N
        addPerfilBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPerfilBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(803, 803, 803)
                            .addComponent(addPerfilBT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(editPerfilBT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(removerPerfilBT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(80, 80, 80)
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAcessar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1)
                .addGap(48, 48, 48)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAcessar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removerPerfilBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addPerfilBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editPerfilBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAcessarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcessarActionPerformed
        
        if(listaPerfis.getSelectedRow() != -1){
            Perfil atual = new Perfil();
            atual.setId(idPerfis.get(listaPerfis.getSelectedRow()));
            atual.setNome((String) listaPerfis.getModel().getValueAt(listaPerfis.getSelectedRow(), 0));
            atual.setCidade((String) listaPerfis.getModel().getValueAt(listaPerfis.getSelectedRow(), 1));
            atual.setRota(rotaDAO.retrieveByColumn("rota", listaPerfis.getValueAt(listaPerfis.getSelectedRow(), 2).toString()).get(0));
            atual.setTamPropriedade((double) listaPerfis.getModel().getValueAt(listaPerfis.getSelectedRow(), 3));
            atual.setAreaPecLeite((double) listaPerfis.getModel().getValueAt(listaPerfis.getSelectedRow(), 4));
            atual.setProdLeiteDiario((double) listaPerfis.getModel().getValueAt(listaPerfis.getSelectedRow(), 5));
            atual.setEmpPermanentes((int) listaPerfis.getModel().getValueAt(listaPerfis.getSelectedRow(), 6));
            atual.setNumFamiliares((int) listaPerfis.getModel().getValueAt(listaPerfis.getSelectedRow(), 7));
        
            ControlePerfil.getInstance().setPerfilSelecionado(atual);

            new MenuPrincipal().setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um perfil na tabela.", "Perfil Não Selecionado.", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_btnAcessarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        
        int escolha = JOptionPane.showOptionDialog(null, "Deseja realmente sair?", "Confirmar logout", JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, null, new String[]{"Sim", "Não"}, "Não");
        
        if(escolha == 0){
            ControleLogin.getInstance().fazerLogout();
        
            new Login().setVisible(true);
            this.setVisible(false);
            this.dispose();
        }
    }//GEN-LAST:event_btnSairActionPerformed

    private void listaPerfisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaPerfisMouseClicked
        if (evt.getClickCount() == 2) {
            btnAcessarActionPerformed(null);
        }
    }//GEN-LAST:event_listaPerfisMouseClicked

    private void removerPerfilBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerPerfilBTActionPerformed
        if(listaPerfis.getSelectedRowCount() > 0) {
           int escolha = JOptionPane.showOptionDialog(null, "Deseja realmente excluir o perfil de nome " 
                   + listaPerfis.getValueAt(listaPerfis.getSelectedRow(), 0).toString().toUpperCase() + " ?", "Exclusão de Perfil", 
                   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"Sim", "Não"}, "Não");
           
           if(escolha == 0)  {
               String input = JOptionPane.showInputDialog(this, "Exclusão de perfil de nome " + 
                       listaPerfis.getValueAt(listaPerfis.getSelectedRow(), 0).toString().toUpperCase() + ".\nDigite seu login para confirmação: ", 
                       "Confirmar Exclusão de Perfil", JOptionPane.OK_CANCEL_OPTION);
 
               if (usuario.getLogin().equals(input)) {
                    listaPerfisGTRE.removeSourceTableRow(listaPerfis.getSelectedRow());
                    verificaTabelaVazia();
               } else if (!usuario.getLogin().equals(input) && input != null){
                   JOptionPane.showMessageDialog(this, "Login Incorreto.", "Login inválido", JOptionPane.ERROR_MESSAGE);
               }
           }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela para remover.", "Remover - Nenhuma linha selecionada", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_removerPerfilBTActionPerformed

    private void editPerfilBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPerfilBTActionPerformed
        listaPerfisGTRE.setEditorType(GenericTableRowEditor.GTRE_UPDATE);
        listaPerfisGTRE.showEditor(evt);
    }//GEN-LAST:event_editPerfilBTActionPerformed

    private void addPerfilBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPerfilBTActionPerformed
        listaPerfisGTRE.setEditorType(GenericTableRowEditor.GTRE_INSERT);
        listaPerfisGTRE.showEditor(evt);
        verificaTabelaVazia();
    }//GEN-LAST:event_addPerfilBTActionPerformed

    private void listaPerfisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listaPerfisKeyPressed
       if (evt.getKeyCode() == 10){
           btnAcessarActionPerformed(null);
       }
    }//GEN-LAST:event_listaPerfisKeyPressed

    private void verificaTabelaVazia() {
        if (listaPerfis.getRowCount() == 0) {
            if (usuario.getLogin().equals("adm")) {
                addPerfilBT.setEnabled(true);
                removerPerfilBT.setEnabled(false);
                editPerfilBT.setEnabled(false);
            } else {
                addPerfilBT.setEnabled(false);
                removerPerfilBT.setEnabled(false);
                editPerfilBT.setEnabled(false);
            }
        } else {
            if (usuario.getLogin().equals("adm")) {
                addPerfilBT.setEnabled(true);
                removerPerfilBT.setEnabled(true);
                editPerfilBT.setEnabled(true);
            } else {
                addPerfilBT.setEnabled(false);
                editPerfilBT.setEnabled(true);
                removerPerfilBT.setEnabled(false);
            }
         }
    }
    
    private void setRotasCombo() {
        JComboBox comboRotas = new JComboBox();
        
        List<Rota> rotas = rotaDAO.retrieveAll();
        
        for(int i = 0; i < rotas.size(); i++) {
            comboRotas.addItem(rotas.get(i).getRota());
        }

        listaPerfisGTRE.getEditTable().getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboRotas));
        
    }
    private void definirBDListeners() {
        
        listaPerfisGTRE.addTableModifyListener((TableModifiedEvent event) -> {
            
            Object[] rowData = event.getTableRowData();
            Integer rowID = (Integer) event.getCustomRowData();
            GenericTableModifier modifier = event.getSourceModifier();
            int modifType = event.getEventType();
            
            if(rowData[2] == null) {
                
                return;
            }
            
            Rota rota = rotaDAO.retrieveByColumn("rota", rowData[2]).get(0);

            switch (modifType) {
                case TableModifiedEvent.ROW_INSERTED:
                {
                    Perfil perfil = new Perfil(Cast.toString(rowData[0]), Cast.toString(rowData[1]),
                            Cast.toDouble(rowData[3]), Cast.toDouble(rowData[4]), Cast.toDouble(rowData[5]), Cast.toInt(rowData[6]),
                            Cast.toInt(rowData[7]), rota);
                    perfilDAO.insert(perfil);
                    idPerfis.add(perfil.getId());
                    modifier.setCustomRowData(modifier.getSourceTable().getRowCount() - 1, perfil.getId());
                    break;
                }
                case TableModifiedEvent.ROW_UPDATED:
                {
                    Perfil perfil = new Perfil(Cast.toString(rowData[0]), Cast.toString(rowData[1]),
                            Cast.toDouble(rowData[3]), Cast.toDouble(rowData[4]), Cast.toDouble(rowData[5]), Cast.toInt(rowData[6]),
                            Cast.toInt(rowData[7]), rota);
                    perfil.setId(rowID);
                    perfilDAO.update(perfil);
                    break;
                }
                case TableModifiedEvent.ROW_DELETED:
                    perfilDAO.remove(rowID);
                    break;
                default:
                    break;
            }

        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPerfilBT;
    private javax.swing.JButton btnAcessar;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton editPerfilBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable listaPerfis;
    private javax.swing.JButton removerPerfilBT;
    // End of variables declaration//GEN-END:variables
}
