/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import controle.ControlePerfil;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.dao.InventarioAnimaisDAO;
import modelo.dao.InventarioBenfeitoriasDAO;
import modelo.dao.InventarioMaquinasDAO;
import modelo.dao.InventarioResumoDAO;
import modelo.dao.InventarioTerrasDAO;
import modelo.negocio.InventarioAnimais;
import modelo.negocio.InventarioBenfeitorias;
import modelo.negocio.InventarioMaquinas;
import modelo.negocio.InventarioResumo;
import modelo.negocio.InventarioTerras;
import modelo.negocio.Perfil;

/**
 *
 * @author Alexandre
 */
public class VisualizarInventario extends javax.swing.JFrame {

    /**
     * Creates new form VisualizarInventario
     */
    public VisualizarInventario() {
        initComponents();
        
        setLocationRelativeTo(null);
        this.setResizable(false);
        
        ArrayList<InventarioTerras> terras = new ArrayList<>();
        ArrayList<InventarioAnimais> animais = new ArrayList<>();
        ArrayList<InventarioBenfeitorias> benfeitorias = new ArrayList<>();
        ArrayList<InventarioMaquinas> maquinas = new ArrayList<>();
        InventarioResumo resumo = new InventarioResumo();
        
        InventarioTerrasDAO itdao = new InventarioTerrasDAO();
        InventarioAnimaisDAO iadao = new InventarioAnimaisDAO();
        InventarioBenfeitoriasDAO ibdao = new InventarioBenfeitoriasDAO();
        InventarioMaquinasDAO imdao = new InventarioMaquinasDAO();
        //InventarioResumoDAO irdao = new InventarioResumoDAO();
        
        Perfil perfilAtual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        System.out.println(perfilAtual.getNome());
        
        try{
            terras = itdao.recuperarPorPerfil(perfilAtual.getIdPerfil());
            
            animais = iadao.recuperarPorPerfil(perfilAtual.getIdPerfil());
            benfeitorias = ibdao.recuperarPorPerfil(perfilAtual.getIdPerfil());
            maquinas = imdao.recuperarPorPerfil(perfilAtual.getIdPerfil());
        }catch(Exception e){
            
        }
     
        DefaultTableModel modelTerras = (DefaultTableModel) tabelaInveTerras.getModel();
        modelTerras.setNumRows(0);
          
        for(int i = 0; i < terras.size(); i++){

            System.out.println("Entrou");
            System.out.println(terras.get(i).getEspecificacao());
            
            modelTerras.addRow(new Object[]{
                terras.get(i).getEspecificacao(),
                terras.get(i).getAreaArrendadaInicio(),
                terras.get(i).getAreaPropriaInicio(),
                terras.get(i).getAreaArrendadaFinal(),
                terras.get(i).getAreaPropriaFinal(),
                terras.get(i).getValorTerraNuaPropria(),
            });
            
        }
        
        DefaultTableModel modelForrageiras = (DefaultTableModel) tabelaInveForrageiras.getModel();
        modelForrageiras.setNumRows(0);
        
        for(int i = 0; i < terras.size(); i++){
            
            double ha = (terras.get(i).getAreaPropriaInicio() + terras.get(i).getAreaPropriaFinal())/2;
            double total = terras.get(i).getCustoFormacaoHectare() * ha;
            modelForrageiras.addRow(new Object[]{
                terras.get(i).getEspecificacao(),
                terras.get(i).getCustoFormacaoHectare(),
                ha,
                total,
                terras.get(i).getVidaUtil(),
                total/terras.get(i).getVidaUtil(),
                
            });
            
        }
        
        DefaultTableModel modelAnimaisProd = (DefaultTableModel) tabelaInveAnimaisProd.getModel();
        modelAnimaisProd.setNumRows(0);
        
        DefaultTableModel modelAnimaisServ = (DefaultTableModel) tabelaInveAnimaisServ.getModel();
        modelAnimaisServ.setNumRows(0);
        
        
        
        for(int i = 0; i < animais.size(); i++){
                
            
             System.out.println("Nome de animal: " + animais.get(0).getTipoAnimal());
             
            if(animais.get(i).getTipoAnimal() == 1){ //Producao
                System.out.println("Nome de animal: " + animais.get(0).getCategoria());
                modelAnimaisProd.addRow(new Object[]{
                    animais.get(i).getCategoria(),
                    animais.get(i).getValorInicio(),
                    animais.get(i).getNascimento(),
                    animais.get(i).getMorte(),
                    animais.get(i).getVenda(),
                    animais.get(i).getCompra(),
                    animais.get(i).getValorFinal(),
                    animais.get(i).getValorCabeca(),
                    animais.get(i).getValorInicio() * animais.get(i).getValorCabeca(),
                    animais.get(i).getValorFinal() * animais.get(i).getValorCabeca(),
                    
                });
                
            } else if (animais.get(i).getTipoAnimal() == 2) { //servico
            
                modelAnimaisServ.addRow(new Object[]{
                    animais.get(i).getCategoria(),
                    animais.get(i).getValorInicio(),
                    animais.get(i).getNascimento(),
                    animais.get(i).getMorte(),
                    animais.get(i).getVenda(),
                    animais.get(i).getCompra(),
                    animais.get(i).getValorFinal(),
                    animais.get(i).getValorCabeca(),

                });
            }
            
        }
        
        DefaultTableModel modelBenfeitorias = (DefaultTableModel) tabelaBenfeitorias.getModel();
        modelBenfeitorias.setNumRows(0);
        
        for(int i = 0; i < benfeitorias.size(); i++){
            
            double total = benfeitorias.get(i).getQuantidade() * benfeitorias.get(i).getValorUnitario();
            
            modelBenfeitorias.addRow(new Object[] {
                benfeitorias.get(i).getEspecificacao(),
                benfeitorias.get(i).getUnidade(),
                benfeitorias.get(i).getQuantidade(),
                benfeitorias.get(i).getValorUnitario(),
                total,
                benfeitorias.get(i).getVidaUtil(),
                total / benfeitorias.get(i).getVidaUtil(),
            });
        }
        
        DefaultTableModel modelMaquinas = (DefaultTableModel) tabelaMaquinas.getModel();
        modelMaquinas.setNumRows(0);
        
        for(int i = 0; i < maquinas.size(); i++){
                
            double total = maquinas.get(i).getQuantidade() * maquinas.get(i).getValorUnitario();
            
            modelMaquinas.addRow(new Object[] {
                maquinas.get(i).getEspecificacao(),
                maquinas.get(i).getUnidade(),
                maquinas.get(i).getQuantidade(),
                maquinas.get(i).getValorUnitario(),
                total,
                maquinas.get(i).getVidaUtil(),
                total / maquinas.get(i).getVidaUtil(),
            });
        }
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaInveTerras = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaInveForrageiras = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        total1 = new javax.swing.JLabel();
        total2 = new javax.swing.JLabel();
        total4 = new javax.swing.JLabel();
        total3 = new javax.swing.JLabel();
        total7 = new javax.swing.JLabel();
        total6 = new javax.swing.JLabel();
        total8 = new javax.swing.JLabel();
        total5 = new javax.swing.JLabel();
        total9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        total10 = new javax.swing.JLabel();
        total11 = new javax.swing.JLabel();
        total12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaInveAnimaisServ = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        total13 = new javax.swing.JLabel();
        total14 = new javax.swing.JLabel();
        total15 = new javax.swing.JLabel();
        total16 = new javax.swing.JLabel();
        total17 = new javax.swing.JLabel();
        total18 = new javax.swing.JLabel();
        total19 = new javax.swing.JLabel();
        total20 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaInveAnimaisProd = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        total21 = new javax.swing.JLabel();
        total22 = new javax.swing.JLabel();
        total23 = new javax.swing.JLabel();
        total24 = new javax.swing.JLabel();
        total25 = new javax.swing.JLabel();
        total26 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        total27 = new javax.swing.JLabel();
        total28 = new javax.swing.JLabel();
        total29 = new javax.swing.JLabel();
        total30 = new javax.swing.JLabel();
        total31 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        total32 = new javax.swing.JLabel();
        total33 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        total34 = new javax.swing.JLabel();
        total35 = new javax.swing.JLabel();
        total36 = new javax.swing.JLabel();
        total37 = new javax.swing.JLabel();
        total38 = new javax.swing.JLabel();
        total39 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelaBenfeitorias = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        total40 = new javax.swing.JLabel();
        total41 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelaMaquinas = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        total42 = new javax.swing.JLabel();
        total43 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        total44 = new javax.swing.JLabel();
        total45 = new javax.swing.JLabel();
        total46 = new javax.swing.JLabel();
        total47 = new javax.swing.JLabel();
        total48 = new javax.swing.JLabel();
        total49 = new javax.swing.JLabel();
        total50 = new javax.swing.JLabel();
        total51 = new javax.swing.JLabel();
        total52 = new javax.swing.JLabel();
        total53 = new javax.swing.JLabel();
        total54 = new javax.swing.JLabel();
        total55 = new javax.swing.JLabel();
        total56 = new javax.swing.JLabel();
        total57 = new javax.swing.JLabel();
        total58 = new javax.swing.JLabel();
        total59 = new javax.swing.JLabel();
        total60 = new javax.swing.JLabel();
        atividadeLeite = new javax.swing.JTextField();
        custoOportunidade = new javax.swing.JTextField();
        salarioMinimo = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();
        textoEntrada = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(38, 81));

        tabelaInveTerras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Especificação", "Área Arrendada INÍCIO", "Área Própria INÍCIO", "Área Arrendada FINAL", "Área Própria FINAL", "Valor da terra Nua Própria"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tabelaInveTerras.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tabelaInveTerras);
        if (tabelaInveTerras.getColumnModel().getColumnCount() > 0) {
            tabelaInveTerras.getColumnModel().getColumn(0).setResizable(false);
            tabelaInveTerras.getColumnModel().getColumn(0).setPreferredWidth(165);
            tabelaInveTerras.getColumnModel().getColumn(1).setResizable(false);
            tabelaInveTerras.getColumnModel().getColumn(2).setResizable(false);
            tabelaInveTerras.getColumnModel().getColumn(3).setResizable(false);
            tabelaInveTerras.getColumnModel().getColumn(4).setResizable(false);
            tabelaInveTerras.getColumnModel().getColumn(5).setResizable(false);
        }

        tabelaInveForrageiras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Forrageiras não Anuiais", "R$/Ha", "Ha", "R$/total", "Vida Útil Anos", "Depreciação R$/Ano"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaInveForrageiras.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelaInveForrageiras);
        if (tabelaInveForrageiras.getColumnModel().getColumnCount() > 0) {
            tabelaInveForrageiras.getColumnModel().getColumn(0).setResizable(false);
            tabelaInveForrageiras.getColumnModel().getColumn(0).setPreferredWidth(165);
            tabelaInveForrageiras.getColumnModel().getColumn(1).setResizable(false);
            tabelaInveForrageiras.getColumnModel().getColumn(2).setResizable(false);
            tabelaInveForrageiras.getColumnModel().getColumn(3).setResizable(false);
            tabelaInveForrageiras.getColumnModel().getColumn(4).setResizable(false);
            tabelaInveForrageiras.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("TOTAL PARA PECUÁRIA");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("ÁREA TOTAL UTILIZADA PARA PECUÁRIA");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("VALOR DA TERRA NUA PRÓPRIA");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("VALOR DA TERRA NUA PRÓPRIA MÉDIO");

        total1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total1.setText("<total1>");

        total2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total2.setText("<total2>");

        total4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total4.setText("<total4>");

        total3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total3.setText("<total3>");

        total7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total7.setText("<total7>");

        total6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total6.setText("<total6>");

        total8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total8.setText("<total8>");

        total5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total5.setText("<total5>");

        total9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total9.setText("<total9>");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("TOTAL");

        total10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total10.setText("<total10>");

        total11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total11.setText("<total11>");

        total12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total12.setText("<total12>");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(total5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(total1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(total2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(total7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(total6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(total3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(total4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(total8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(total9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(167, 167, 167))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151)
                .addComponent(total10, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total11, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(total12, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(total1)
                    .addComponent(total2)
                    .addComponent(total3)
                    .addComponent(total4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(total5)
                    .addComponent(total6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(total7)
                    .addComponent(total8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(total9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(total10)
                    .addComponent(total11)
                    .addComponent(total12))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Terras", jPanel1);

        tabelaInveAnimaisServ.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "", "", "", "", "", "", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class
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
        tabelaInveAnimaisServ.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tabelaInveAnimaisServ);
        if (tabelaInveAnimaisServ.getColumnModel().getColumnCount() > 0) {
            tabelaInveAnimaisServ.getColumnModel().getColumn(0).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(0).setPreferredWidth(200);
            tabelaInveAnimaisServ.getColumnModel().getColumn(1).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(1).setPreferredWidth(50);
            tabelaInveAnimaisServ.getColumnModel().getColumn(2).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabelaInveAnimaisServ.getColumnModel().getColumn(2).setHeaderValue("");
            tabelaInveAnimaisServ.getColumnModel().getColumn(3).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(3).setPreferredWidth(50);
            tabelaInveAnimaisServ.getColumnModel().getColumn(3).setHeaderValue("");
            tabelaInveAnimaisServ.getColumnModel().getColumn(4).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(4).setPreferredWidth(50);
            tabelaInveAnimaisServ.getColumnModel().getColumn(4).setHeaderValue("");
            tabelaInveAnimaisServ.getColumnModel().getColumn(5).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(5).setPreferredWidth(50);
            tabelaInveAnimaisServ.getColumnModel().getColumn(5).setHeaderValue("");
            tabelaInveAnimaisServ.getColumnModel().getColumn(6).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(6).setPreferredWidth(50);
            tabelaInveAnimaisServ.getColumnModel().getColumn(6).setHeaderValue("");
            tabelaInveAnimaisServ.getColumnModel().getColumn(7).setResizable(false);
            tabelaInveAnimaisServ.getColumnModel().getColumn(7).setHeaderValue("");
        }

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("TOTAL DE ANIMAIS DE PRODUÇÃO");

        total13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total13.setText("<total13>");

        total14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total14.setText("<total14>");

        total15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total15.setText("<total15>");

        total16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total16.setText("<total16>");

        total17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total17.setText("<total17>");

        total18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total18.setText("<total18>");

        total19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total19.setText("<total19>");

        total20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total20.setText("<total20>");

        tabelaInveAnimaisProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Categoria", "Início", "Nasci/to", "Morte", "Venda", "Compra", "Final", "Valor (R$/Cab)", "Valor Inicial", "Valor Final"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaInveAnimaisProd.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tabelaInveAnimaisProd);
        if (tabelaInveAnimaisProd.getColumnModel().getColumnCount() > 0) {
            tabelaInveAnimaisProd.getColumnModel().getColumn(0).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(0).setPreferredWidth(200);
            tabelaInveAnimaisProd.getColumnModel().getColumn(1).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(1).setPreferredWidth(50);
            tabelaInveAnimaisProd.getColumnModel().getColumn(2).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabelaInveAnimaisProd.getColumnModel().getColumn(3).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(3).setPreferredWidth(50);
            tabelaInveAnimaisProd.getColumnModel().getColumn(4).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(4).setPreferredWidth(50);
            tabelaInveAnimaisProd.getColumnModel().getColumn(5).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(5).setPreferredWidth(50);
            tabelaInveAnimaisProd.getColumnModel().getColumn(6).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(6).setPreferredWidth(50);
            tabelaInveAnimaisProd.getColumnModel().getColumn(7).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(8).setResizable(false);
            tabelaInveAnimaisProd.getColumnModel().getColumn(9).setResizable(false);
        }

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("TOTAL DE ANIMAIS DE SERVIÇO");

        total21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total21.setText("<total21>");

        total22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total22.setText("<total22>");

        total23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total23.setText("<total23>");

        total24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total24.setText("<total24>");

        total25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total25.setText("<total25>");

        total26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total26.setText("<total26>");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("TOTAL DE ANIMAIS");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("TOTAL (Calculado)");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("VALOR MÉDIO DO REBANHO DE PRODUÇÃO");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Valor gasto com compra de Animais");

        total27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total27.setText("<total27>");

        total28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total28.setText("<total28>");

        total29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total29.setText("<total29>");

        total30.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total30.setText("<total30>");

        total31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total31.setText("<total31>");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("VARIAÇÃO DE INVENTÁRIO ANIMAL");

        total32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total32.setText("<total32>");

        total33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total33.setText("<total33>");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Dados Adicionais");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Capital Investido em Reprodutores");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Vida Útil dos Reprodutores (anos)");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Depreciação dos Reprodutores - R$/ano");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Capital Investido em Animais de Serviços");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Vida Útil dos Animais de Serviços (anos)");

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Depreciação dos Animais de Serviços - R$/ano");

        total34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total34.setText("<total34>");

        total35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total35.setText("<total35>");

        total36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total36.setText("<total36>");

        total37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total37.setText("<total37>");

        total38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total38.setText("<total38>");

        total39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total39.setText("<total39>");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total13, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(total14, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(total15, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(total16, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total17, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total18, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addComponent(total19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total20, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(total21, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                        .addComponent(total28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(total22, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(total32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(total34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(total35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(total36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(total23, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(total24, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(total25, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(total29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(total26, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(total30, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                        .addComponent(total27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(total33, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(total37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(total38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(total39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(total13)
                    .addComponent(total20)
                    .addComponent(total19)
                    .addComponent(total14)
                    .addComponent(total15)
                    .addComponent(total16)
                    .addComponent(total17)
                    .addComponent(total18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(total21)
                    .addComponent(total22)
                    .addComponent(total23)
                    .addComponent(total24)
                    .addComponent(total25)
                    .addComponent(total26)
                    .addComponent(total27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(total28)
                    .addComponent(total29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(total30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(total31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(total32)
                    .addComponent(jLabel11)
                    .addComponent(total33))
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel17)
                    .addComponent(total34)
                    .addComponent(total37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel18)
                    .addComponent(total35)
                    .addComponent(total38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel19)
                    .addComponent(total36)
                    .addComponent(total39))
                .addGap(0, 31, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Animais", jPanel2);

        tabelaBenfeitorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Especificação", "Unidade", "Quantidade", "Valor Unitário (R$)", "Valor Total (R$)", "Vida Útil (anos)", "R$/ano"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaBenfeitorias.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tabelaBenfeitorias);
        if (tabelaBenfeitorias.getColumnModel().getColumnCount() > 0) {
            tabelaBenfeitorias.getColumnModel().getColumn(0).setResizable(false);
            tabelaBenfeitorias.getColumnModel().getColumn(0).setPreferredWidth(200);
            tabelaBenfeitorias.getColumnModel().getColumn(1).setResizable(false);
            tabelaBenfeitorias.getColumnModel().getColumn(2).setResizable(false);
            tabelaBenfeitorias.getColumnModel().getColumn(3).setResizable(false);
            tabelaBenfeitorias.getColumnModel().getColumn(4).setResizable(false);
            tabelaBenfeitorias.getColumnModel().getColumn(5).setResizable(false);
            tabelaBenfeitorias.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("DEPRECIAÇÃO");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("VALOR DO PREÇO DE NOVO");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("TOTAL");

        total40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total40.setText("<total40>");

        total41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total41.setText("<total41>");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(249, 249, 249)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124)
                .addComponent(total40, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                .addComponent(total41, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(total40)
                    .addComponent(total41))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Benfeitorias", jPanel3);

        tabelaMaquinas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Especificação", "Unidade", "Quantidade", "Valor Unitário (R$)", "Valor Total (R$)", "Vida Útil (anos)", "R$/ano"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaMaquinas.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tabelaMaquinas);
        if (tabelaMaquinas.getColumnModel().getColumnCount() > 0) {
            tabelaMaquinas.getColumnModel().getColumn(0).setResizable(false);
            tabelaMaquinas.getColumnModel().getColumn(0).setPreferredWidth(200);
            tabelaMaquinas.getColumnModel().getColumn(1).setResizable(false);
            tabelaMaquinas.getColumnModel().getColumn(2).setResizable(false);
            tabelaMaquinas.getColumnModel().getColumn(3).setResizable(false);
            tabelaMaquinas.getColumnModel().getColumn(4).setResizable(false);
            tabelaMaquinas.getColumnModel().getColumn(5).setResizable(false);
            tabelaMaquinas.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("VALOR DO PREÇO DE NOVO");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("DEPRECIAÇÃO");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("TOTAL");

        total42.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total42.setText("<total42>");

        total43.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total43.setText("<total43>");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125)
                        .addComponent(total42, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(total43, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(total42)
                    .addComponent(total43))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Máquinas", jPanel4);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel26.setText("RESUMO DA DEPRECIAÇÃO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel26, gridBagConstraints);

        jLabel27.setText("Forrageiras não anuais");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel27, gridBagConstraints);

        jLabel28.setText("Animais de trabalho");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel28, gridBagConstraints);

        jLabel29.setText("Reprodutores");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel29, gridBagConstraints);

        jLabel30.setText("Benfeitorias utilizadas para pecuária de leite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel30, gridBagConstraints);

        jLabel31.setText("Máquinas utilizadas na pecuária de leite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel31, gridBagConstraints);

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setText("Total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel32, gridBagConstraints);

        jLabel33.setText("Leite/atividade leiteira");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel33, gridBagConstraints);

        jLabel34.setText("Depreciação do leite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel34, gridBagConstraints);

        jLabel35.setText("RESUMO DO INVENTÁRIO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel35, gridBagConstraints);

        jLabel36.setText("Terras");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel36, gridBagConstraints);

        jLabel37.setText("Forrageiras não anuais");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel37, gridBagConstraints);

        jLabel38.setText("Animais");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel38, gridBagConstraints);

        jLabel39.setText("Benfeitorias utilizadas na pecuária de leite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel39, gridBagConstraints);

        jLabel40.setText("Máquinas utilizadas na pecuária de leite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel40, gridBagConstraints);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setText("Total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel41, gridBagConstraints);

        jLabel42.setText("Salário mínimo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel42, gridBagConstraints);

        jLabel43.setText("   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 9;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel5.add(jLabel43, gridBagConstraints);

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel44.setText("Capital empatado leite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel44, gridBagConstraints);

        jLabel45.setText("Custo de oportunidade");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel45, gridBagConstraints);

        jLabel46.setText("MÃO DE OBRA FAMILIAR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel46, gridBagConstraints);

        jLabel47.setText("Décimo terceiro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel47, gridBagConstraints);

        jLabel48.setText("Terço de férias");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel48, gridBagConstraints);

        total44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total44.setText("<total44>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total44, gridBagConstraints);

        total45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total45.setText("<total45>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total45, gridBagConstraints);

        total46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total46.setText("<total46>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total46, gridBagConstraints);

        total47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total47.setText("<total47>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total47, gridBagConstraints);

        total48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total48.setText("<total48>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total48, gridBagConstraints);

        total49.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total49.setText("<total49>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total49, gridBagConstraints);

        total50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total50.setText("<total50>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total50, gridBagConstraints);

        total51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total51.setText("<total51>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total51, gridBagConstraints);

        total52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total52.setText("<total52>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total52, gridBagConstraints);

        total53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total53.setText("<total53>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total53, gridBagConstraints);

        total54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total54.setText("<total54>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total54, gridBagConstraints);

        total55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total55.setText("<total55>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total55, gridBagConstraints);

        total56.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total56.setText("<total56>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total56, gridBagConstraints);

        total57.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total57.setText("<total57>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total57, gridBagConstraints);

        total58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total58.setText("<total58>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total58, gridBagConstraints);

        total59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total59.setText("<total59>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total59, gridBagConstraints);

        total60.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        total60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        total60.setText("<total60>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(total60, gridBagConstraints);

        atividadeLeite.setText("<ativ>");
        atividadeLeite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atividadeLeiteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(atividadeLeite, gridBagConstraints);

        custoOportunidade.setText("<custo>");
        custoOportunidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                custoOportunidadeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(custoOportunidade, gridBagConstraints);

        salarioMinimo.setText("<salario>");
        salarioMinimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salarioMinimoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 1);
        jPanel5.add(salarioMinimo, gridBagConstraints);

        jLabel49.setText("   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 9;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel5.add(jLabel49, gridBagConstraints);

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel50.setText("Custo total do salário mensal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        jPanel5.add(jLabel50, gridBagConstraints);

        jTabbedPane1.addTab("Resumo", jPanel5);

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        textoEntrada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        textoEntrada.setForeground(new java.awt.Color(51, 153, 255));
        textoEntrada.setText("INVENTÁRIO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textoEntrada)
                .addGap(381, 381, 381))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar)
                    .addComponent(textoEntrada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        
        new MenuPrincipal().setVisible(true);
        this.setVisible(false);
        this.dispose();
        
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void custoOportunidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_custoOportunidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_custoOportunidadeActionPerformed

    private void salarioMinimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salarioMinimoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_salarioMinimoActionPerformed

    private void atividadeLeiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atividadeLeiteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_atividadeLeiteActionPerformed

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
            java.util.logging.Logger.getLogger(VisualizarInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisualizarInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisualizarInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisualizarInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisualizarInventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField atividadeLeite;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JTextField custoOportunidade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField salarioMinimo;
    private javax.swing.JTable tabelaBenfeitorias;
    private javax.swing.JTable tabelaInveAnimaisProd;
    private javax.swing.JTable tabelaInveAnimaisServ;
    private javax.swing.JTable tabelaInveForrageiras;
    private javax.swing.JTable tabelaInveTerras;
    private javax.swing.JTable tabelaMaquinas;
    private javax.swing.JLabel textoEntrada;
    private javax.swing.JLabel total1;
    private javax.swing.JLabel total10;
    private javax.swing.JLabel total11;
    private javax.swing.JLabel total12;
    private javax.swing.JLabel total13;
    private javax.swing.JLabel total14;
    private javax.swing.JLabel total15;
    private javax.swing.JLabel total16;
    private javax.swing.JLabel total17;
    private javax.swing.JLabel total18;
    private javax.swing.JLabel total19;
    private javax.swing.JLabel total2;
    private javax.swing.JLabel total20;
    private javax.swing.JLabel total21;
    private javax.swing.JLabel total22;
    private javax.swing.JLabel total23;
    private javax.swing.JLabel total24;
    private javax.swing.JLabel total25;
    private javax.swing.JLabel total26;
    private javax.swing.JLabel total27;
    private javax.swing.JLabel total28;
    private javax.swing.JLabel total29;
    private javax.swing.JLabel total3;
    private javax.swing.JLabel total30;
    private javax.swing.JLabel total31;
    private javax.swing.JLabel total32;
    private javax.swing.JLabel total33;
    private javax.swing.JLabel total34;
    private javax.swing.JLabel total35;
    private javax.swing.JLabel total36;
    private javax.swing.JLabel total37;
    private javax.swing.JLabel total38;
    private javax.swing.JLabel total39;
    private javax.swing.JLabel total4;
    private javax.swing.JLabel total40;
    private javax.swing.JLabel total41;
    private javax.swing.JLabel total42;
    private javax.swing.JLabel total43;
    private javax.swing.JLabel total44;
    private javax.swing.JLabel total45;
    private javax.swing.JLabel total46;
    private javax.swing.JLabel total47;
    private javax.swing.JLabel total48;
    private javax.swing.JLabel total49;
    private javax.swing.JLabel total5;
    private javax.swing.JLabel total50;
    private javax.swing.JLabel total51;
    private javax.swing.JLabel total52;
    private javax.swing.JLabel total53;
    private javax.swing.JLabel total54;
    private javax.swing.JLabel total55;
    private javax.swing.JLabel total56;
    private javax.swing.JLabel total57;
    private javax.swing.JLabel total58;
    private javax.swing.JLabel total59;
    private javax.swing.JLabel total6;
    private javax.swing.JLabel total60;
    private javax.swing.JLabel total7;
    private javax.swing.JLabel total8;
    private javax.swing.JLabel total9;
    // End of variables declaration//GEN-END:variables
}
