/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao.windows;

import controle.ControleLogin;
import flex.db.GenericDAO;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.negocio.Perfil;
import modelo.negocio.Usuario;

/**
 *
 * @author Alexandre
 */
public class GerenciarUsuarios extends javax.swing.JFrame {

    private final GenericDAO<Usuario> udao;
    private List<Usuario> usuarios;
    private final Usuario usuarioAtual;
    
    /**
     * Creates new form GerenciarUsuarios
     */
    public GerenciarUsuarios() {
        initComponents();
        
        Image image = new ImageIcon(getClass().getResource("/visao/images/cattle.png")).getImage();
        this.setIconImage(image);
        
        super.setTitle("SGPL - Gerenciar Usuários");
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        
        usuarioAtual = ControleLogin.getInstance().getUsuario();
        
        udao = new GenericDAO<>(Usuario.class);
        usuarios = new ArrayList<>();
        
        usuarios = udao.retrieveAll();
        
        for(int i = 0; i < usuarios.size(); i++){
            jComboBoxUsuario.addItem(usuarios.get(i));
        }
        
        listDisp.setEnabled(false);
        listSele.setEnabled(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroupPermissao = new javax.swing.ButtonGroup();
        textoEntrada = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addUsuarioBT = new javax.swing.JButton();
        jComboBoxUsuario = new javax.swing.JComboBox();
        editUsuarioBT = new javax.swing.JButton();
        deleteUsuarioBT = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listDisp = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        listSele = new javax.swing.JList();
        btnDirTod = new javax.swing.JButton();
        btnEsqUni = new javax.swing.JButton();
        btnEsqTod = new javax.swing.JButton();
        btnDirUni = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textoEntrada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        textoEntrada.setForeground(new java.awt.Color(0, 38, 255));
        textoEntrada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textoEntrada.setText("GERENCIAR USUÁRIOS");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("Usuário:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(jLabel1, gridBagConstraints);

        addUsuarioBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/add.png"))); // NOI18N
        addUsuarioBT.setToolTipText("Novo Usuário.");
        addUsuarioBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUsuarioBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = -24;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 15);
        jPanel1.add(addUsuarioBT, gridBagConstraints);

        jComboBoxUsuario.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jComboBoxUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione um usuário..." }));
        ativarComponentes(false);
        jComboBoxUsuario.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxUsuarioItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 143;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 15);
        jPanel1.add(jComboBoxUsuario, gridBagConstraints);

        editUsuarioBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit.png"))); // NOI18N
        editUsuarioBT.setToolTipText("Editar Usuário.");
        editUsuarioBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUsuarioBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = -24;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 15);
        jPanel1.add(editUsuarioBT, gridBagConstraints);

        deleteUsuarioBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/delete.png"))); // NOI18N
        deleteUsuarioBT.setToolTipText("Deletar Usuário.");
        deleteUsuarioBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUsuarioBTActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = -24;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        jPanel1.add(deleteUsuarioBT, gridBagConstraints);

        listDisp.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Selecione um usuário" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listDisp);

        listSele.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Selecione um usuário" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listSele.setToolTipText("Perfis que o usuário terá acesso.");
        jScrollPane2.setViewportView(listSele);

        btnDirTod.setText(">>");
        btnDirTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirTodActionPerformed(evt);
            }
        });

        btnEsqUni.setText("<");
        btnEsqUni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsqUniActionPerformed(evt);
            }
        });

        btnEsqTod.setText("<<");
        btnEsqTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEsqTodActionPerformed(evt);
            }
        });

        btnDirUni.setText(">");
        btnDirUni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirUniActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("Perfis disponíveis");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setText("Perfis que o usuário terá acesso");

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Voltar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Segure Ctrl e clique nos perfis para selecionar vários");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 985, Short.MAX_VALUE)
                            .addComponent(textoEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEsqTod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEsqUni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDirTod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDirUni, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(175, 175, 175))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textoEntrada)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(77, 77, 77)
                            .addComponent(btnDirUni)
                            .addGap(18, 18, 18)
                            .addComponent(btnDirTod)
                            .addGap(18, 18, 18)
                            .addComponent(btnEsqUni)
                            .addGap(18, 18, 18)
                            .addComponent(btnEsqTod)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addUsuarioBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUsuarioBTActionPerformed
        AddUsuario telaAddUsu = new AddUsuario(this, true);
        telaAddUsu.setTitle("SGPL - Cadastro de Usuário");
        telaAddUsu.setVisible(true);
        
        if(telaAddUsu.getUsuarioSelecionado() != null){
            jComboBoxUsuario.addItem(telaAddUsu.getUsuarioSelecionado());
            jComboBoxUsuario.setSelectedIndex(jComboBoxUsuario.getItemCount() - 1);
        }
        
    }//GEN-LAST:event_addUsuarioBTActionPerformed

    private void btnDirTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirTodActionPerformed

        btnCancelar.setText("Cancelar");
        
        DefaultListModel listModelDisp = (DefaultListModel) listDisp.getModel();
        DefaultListModel listModelSele = (DefaultListModel) listSele.getModel();
        
        List<Perfil> perfisSelecionados;
        
        listDisp.setSelectionInterval(0, listModelDisp.size() - 1);
        
        perfisSelecionados = listDisp.getSelectedValuesList();
        
        for(int i = 0; i < perfisSelecionados.size(); i++){
            
            listModelSele.addElement(perfisSelecionados.get(i));
            listModelDisp.removeElement(perfisSelecionados.get(i));
            
        }
        
        listDisp.setModel(listModelDisp);
        listSele.setModel(listModelSele);
        
    }//GEN-LAST:event_btnDirTodActionPerformed

    private void btnEsqUniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsqUniActionPerformed

        btnCancelar.setText("Cancelar");
        
        DefaultListModel listModelDisp = (DefaultListModel) listDisp.getModel();
        DefaultListModel listModelSele = (DefaultListModel) listSele.getModel();
        
        List<Perfil> perfisSelecionados;
        
        perfisSelecionados = listSele.getSelectedValuesList();
        
        for(int i = 0; i < perfisSelecionados.size(); i++){
            
            listModelDisp.addElement(perfisSelecionados.get(i));
            listModelSele.removeElement(perfisSelecionados.get(i));
            
        }
        
        listDisp.setModel(listModelDisp);
        listSele.setModel(listModelSele);
        
    }//GEN-LAST:event_btnEsqUniActionPerformed

    private void btnEsqTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEsqTodActionPerformed
        
        btnCancelar.setText("Cancelar");
        
        DefaultListModel listModelDisp = (DefaultListModel) listDisp.getModel();
        DefaultListModel listModelSele = (DefaultListModel) listSele.getModel();
        
        List<Perfil> perfisSelecionados;
        
        listSele.setSelectionInterval(0, listModelSele.size() - 1);
        
        perfisSelecionados = listSele.getSelectedValuesList();
        
        for(int i = 0; i < perfisSelecionados.size(); i++){
            
            listModelDisp.addElement(perfisSelecionados.get(i));
            listModelSele.removeElement(perfisSelecionados.get(i));
            
        }
        
        listDisp.setModel(listModelDisp);
        listSele.setModel(listModelSele);
        
    }//GEN-LAST:event_btnEsqTodActionPerformed

    private void btnDirUniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirUniActionPerformed
        
        btnCancelar.setText("Cancelar");
        
        DefaultListModel listModelDisp = (DefaultListModel) listDisp.getModel();
        DefaultListModel listModelSele = (DefaultListModel) listSele.getModel();
        
        List<Perfil> perfisSelecionados;
        
        perfisSelecionados = listDisp.getSelectedValuesList();
        
        for(int i = 0; i < perfisSelecionados.size(); i++){
            
            listModelSele.addElement(perfisSelecionados.get(i));
            listModelDisp.removeElement(perfisSelecionados.get(i));
            
        }
        
        listDisp.setModel(listModelDisp);
        listSele.setModel(listModelSele);
                
    }//GEN-LAST:event_btnDirUniActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        
        if( jComboBoxUsuario.getSelectedIndex() != 0 ){
        
            Usuario usuario = (Usuario) jComboBoxUsuario.getSelectedItem();
            
            GenericDAO<Perfil> pdao = new GenericDAO<>(Perfil.class);
            
            List<Perfil> perfisAntigos = new ArrayList<>();
            List<Perfil> perfisNovos   = new ArrayList<>();
            Boolean[] perfisExcluir, perfisAdd;
            
            DefaultListModel listModelSele = (DefaultListModel) listSele.getModel();

            listSele.setSelectionInterval(0, listModelSele.size() - 1);
            perfisNovos = listSele.getSelectedValuesList();
            
            if( usuario.getTipoUsuario() != 1){

                perfisAntigos = pdao.executeSQL("SELECT idPerfil, nome, cidade, tamPropriedade, areaPecLeite, prodLeiteDiario, empPermanentes, numFamiliares, idRotaFK "
                                                + "FROM usuario_perfil AS up, perfil AS p "
                                                + "WHERE up.idUsuarioFK = " + usuario.getId() + " AND up.idPerfilFK = p.idPerfil");
                
                perfisExcluir = new Boolean[perfisAntigos.size()];
                perfisAdd     = new Boolean[perfisNovos.size()];
                
                for(int i = 0; i < perfisNovos.size(); i++){

                    for(int j = 0; j < perfisAntigos.size(); j++){
                        
                        if( perfisNovos.get(i).getNome().equals(perfisAntigos.get(j).getNome())){
                            
                            perfisAdd[i] = true;
                            perfisExcluir[j] = true;
                            
                            break;
                            
                        } else {
                            
                        }

                    }

                }
                                
                for(int i = 0; i < perfisAdd.length; i++){
                    
                    if(perfisAdd[i] == null){
                        pdao.executeSQL("INSERT INTO usuario_perfil (idUsuarioFK, idPerfilFK) "
                                      + "VALUES (" + usuario.getId() + ", " + perfisNovos.get(i).getId() + ")");
                    }
                    

                    
                }
                
                for(int i = 0; i < perfisExcluir.length; i++){
                
                    if(perfisExcluir[i] == null){
                    pdao.executeSQL("DELETE FROM usuario_perfil "
                                    + "WHERE idUsuarioFK = " + usuario.getId() + " "
                                    + "AND idPerfilFK = " + perfisAntigos.get(i).getId());
                    }
                }

            }

            JOptionPane.showMessageDialog(null, "As alterações foram salvas");
            
            btnCancelar.setText("Voltar");
            
        }
        
        
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        new VisualizarPerfil().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jComboBoxUsuarioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxUsuarioItemStateChanged
        
        if(evt.getStateChange() == ItemEvent.SELECTED) { 
            if(jComboBoxUsuario.getSelectedIndex() != 0){
                System.out.println("Entrou no item " + jComboBoxUsuario.getSelectedIndex());
                
                preencherListas((Usuario) jComboBoxUsuario.getSelectedItem());
            } else {
                ativarComponentes(false);
            }
        }
    }//GEN-LAST:event_jComboBoxUsuarioItemStateChanged

    private void editUsuarioBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUsuarioBTActionPerformed
        if( jComboBoxUsuario.getSelectedIndex() == 0 ){
            JOptionPane.showMessageDialog(null, "Selecione um usuário!", "Editar - Nenhum usuário selecionado", 
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            AddUsuario telaAddUsu = new AddUsuario(this, true);
            telaAddUsu.setTitle("SGPL - Cadastro de Usuário");
            telaAddUsu.prepararParaEdicao((Usuario) jComboBoxUsuario.getSelectedItem());
            telaAddUsu.setVisible(true); 
            
            preencherListas((Usuario) jComboBoxUsuario.getSelectedItem());
        }
    }//GEN-LAST:event_editUsuarioBTActionPerformed

    private void deleteUsuarioBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUsuarioBTActionPerformed
        GenericDAO<Usuario> udao = new GenericDAO<>(Usuario.class);
        
        if(jComboBoxUsuario.getSelectedIndex() != 0) {
            int escolha = JOptionPane.showOptionDialog(null, "Deseja realmente excluir o usuário " 
                    + jComboBoxUsuario.getSelectedItem().toString().toUpperCase() + " ?", "Exclusão de Usuário", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"Sim", "Não"}, "Não");
           
            if(escolha == 0)  {
                String input = JOptionPane.showInputDialog(this, "Exclusão do usuário " + 
                        jComboBoxUsuario.getSelectedItem().toString().toUpperCase() + ".\nDigite seu login para confirmação: ", 
                        "Confirmar Exclusão de Perfil", JOptionPane.OK_CANCEL_OPTION);
 
                if (usuarioAtual.getLogin().equals(input)) {
                     Usuario usuario = (Usuario) jComboBoxUsuario.getSelectedItem();
                     jComboBoxUsuario.removeItem(usuario);
                     
                     udao.executeSQL("DELETE FROM usuario_perfil "
                                    + "WHERE idUsuarioFK = " + usuario.getId());
                     
                     udao.remove(usuario.getId());
                     
                     JOptionPane.showMessageDialog(null, "O usuário " + usuario.getLogin().toUpperCase() + " foi removido.");
                     
                } else if (!usuarioAtual.getLogin().equals(input) && input != null){
                    JOptionPane.showMessageDialog(this, "Login Incorreto.", "Login inválido", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um usuário!", "Remover - Nenhum usuário selecionado", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_deleteUsuarioBTActionPerformed

    
    public void preencherListas(Usuario usuario) {
        
        //--RadioButton---------------------------
        int tipoUsu = usuario.getTipoUsuario();
        
        switch (tipoUsu) {
            case 1:
                ativarComponentes(false);                
                break;
            case 2:
                ativarComponentes(true);
                break;
            case 3:
                ativarComponentes(true);
                break;
            default:
                break;
        }
        
        //--Listas--------------------------------
        
        //listDisp.removeAll();
        //listSele.removeAll();
        
        GenericDAO<Perfil> pdao = new GenericDAO<>(Perfil.class);
        
        DefaultListModel listModelDisp;
        DefaultListModel listModelSele;        
        
        List<Perfil> todosPerfis = new ArrayList<>();
        List<Perfil> perfisSelec = new ArrayList<>();
        
        todosPerfis = pdao.retrieveAll();
        
        if( tipoUsu == 1 ){
            
            listModelDisp = new DefaultListModel();
            listModelSele = new DefaultListModel();
                        
            for(int i = 0; i < todosPerfis.size(); i++){
                listModelSele.addElement(todosPerfis.get(i));
            }
            
            
        } else {
            
            listModelDisp = new DefaultListModel();
            listModelSele = new DefaultListModel();
            
            perfisSelec = pdao.executeSQL("SELECT idPerfil, nome, cidade, tamPropriedade, areaPecLeite, prodLeiteDiario, empPermanentes, numFamiliares, idRotaFK "
                                        + "FROM usuario_perfil AS up, perfil AS p "
                                        + "WHERE up.idUsuarioFK = " + usuario.getId() + " AND up.idPerfilFK = p.idPerfil");
                        
            for(int i = 0; i < todosPerfis.size(); i++){
                listModelDisp.addElement(todosPerfis.get(i));
            }
            
            for(int i = 0; i < perfisSelec.size(); i++){
                listModelSele.addElement(perfisSelec.get(i));
            }
            
            for(int i = 0; i < todosPerfis.size(); i++){
            
                for(int j = 0; j < perfisSelec.size(); j++){
                    if(todosPerfis.get(i).getId() == perfisSelec.get(j).getId()){
                        listModelDisp.removeElement(todosPerfis.get(i));
                        break;
                    } 
                    
                }

            }
        
        
        }
        
        listDisp.setModel(listModelDisp);
        listSele.setModel(listModelSele);
        
    }
    
    public void ativarComponentes(boolean estado){
        
        btnDirUni.setEnabled(estado);
        btnDirTod.setEnabled(estado);
        btnEsqUni.setEnabled(estado);
        btnEsqTod.setEnabled(estado);
        
        listDisp.setEnabled(estado);
        listSele.setEnabled(estado);
                
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addUsuarioBT;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDirTod;
    private javax.swing.JButton btnDirUni;
    private javax.swing.JButton btnEsqTod;
    private javax.swing.JButton btnEsqUni;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroupPermissao;
    private javax.swing.JButton deleteUsuarioBT;
    private javax.swing.JButton editUsuarioBT;
    private javax.swing.JComboBox jComboBoxUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList listDisp;
    private javax.swing.JList listSele;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
