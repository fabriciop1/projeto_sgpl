/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao.windows;

import controle.ControlePerfil;
import controle.ControleRelatoriosMensais;
import flex.db.GenericDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.negocio.DadosEconMensais;
import modelo.negocio.DadosTecMensais;
import modelo.negocio.Perfil;
import util.Util;

/**
 * @author Alexandre
 */
public class VisualizarIndicadoresMensais extends javax.swing.JFrame {

    private final Perfil atual;
    private List<DadosEconMensais> dems;
    private List<DadosTecMensais>  dtms;
    private GenericDAO<DadosEconMensais> demdao;
    private GenericDAO<DadosTecMensais>  dtmdao;
    private final ControleRelatoriosMensais crm;
    
    /**
     * Creates new form VisualizarRelatoriosMensais
     */
    public VisualizarIndicadoresMensais() {
        initComponents();
        
        tabelaRelatorios.setShowGrid(true);
        tabelaRelatorios.setShowHorizontalLines(true);
        tabelaRelatorios.setCellSelectionEnabled(false);
        tabelaRelatorios.setRowSelectionAllowed(true);
        
        crm = ControleRelatoriosMensais.getInstance();
        
        atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setTitle("SGPL - " + atual.getNome() + " - Indicadores Mensais");
        
        
            
        demdao = new GenericDAO<>(DadosEconMensais.class);
        dems = demdao.executeSQL("SELECT * "
                            + "FROM dados_economicos_mensais AS d "
                            + "WHERE (d.ano >= " + crm.getAnoIni() + "  AND "
                                   + "d.ano <= " + crm.getAnoFim() + "  AND "
                                   + "d.mes >= " + crm.getMesIni() + "  AND "
                                   + "d.mes <= " + crm.getMesFim() + ") AND "
                                   + "d.idPerfilFK = " + atual.getId()
                            + " ORDER BY d.ano, d.mes");
        
        dtmdao = new GenericDAO<>(DadosTecMensais.class);
        dtms = dtmdao.executeSQL("SELECT * "
                            + "FROM dados_tecnicos_mensais AS d "
                            + "WHERE (d.ano >= " + crm.getAnoIni() + "  AND "
                                   + "d.ano <= " + crm.getAnoFim() + "  AND "
                                   + "d.mes >= " + crm.getMesIni() + "  AND "
                                   + "d.mes <= " + crm.getMesFim() + ") AND "
                                   + "d.idPerfilFK = " + atual.getId()
                            + " ORDER BY d.ano, d.mes");
                
        if( crm.getTipo() == 1 ){    
            preencherTabelaIEM(dems);            
        } else if( crm.getTipo() == 2 ){
            preencherTabelaITM(dtms);
        }
    }
    
    private void preencherTabelaIEM(List<DadosEconMensais> iem){
        
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaRelatorios.getModel();
        modelIndicadores.setNumRows(0);
        
        int anoCont = crm.getAnoIni();
        int anoFim  = crm.getAnoFim();
        int mesCont = crm.getMesIni() - 1;
        int mesFim  = crm.getMesFim();
        Object[] temp = new Object[32];
                
        modelIndicadores.addColumn("Indicadores", getIEM());
        tabelaRelatorios.getColumnModel().getColumn(0).setPreferredWidth(300);
        
        do{
            for(int i = 0; i < iem.size(); i++){
                
                DadosEconMensais dem = iem.get(i);
                
                if(dem.getEspecificacao().getEspecificacao().equals("Venda de Leite (L)")
                        && dem.getMes() == mesCont && dem.getAno() == anoCont){
                    temp[0] = dem.getQuantidade() * dem.getValorUnitario();
                    break;
                }
                
                if((getTotalLitroLeite(dtms, mesCont, anoCont) != -1 || getTotalLitroLeite(dtms, mesCont, anoCont) != 0.0)
                        && temp[0] != null){
                    temp[1] = (Double) temp[0]/getTotalLitroLeite(dtms, mesCont, anoCont);
                }
            }
            
            
            
            
            
            mesCont++;
            
            if( mesCont > 12 ){
                mesCont = 1;
                anoCont++;
            }
            
            //----FINAL---------------------------------------------------------
            modelIndicadores.addColumn(mesCont + "/" + anoCont, temp);
           
            Util.clearVector(temp);
        }while(anoCont != anoFim || mesCont != mesFim);
        
        tabelaRelatorios.setModel(modelIndicadores);
    }
    
    private void preencherTabelaITM(List<DadosTecMensais> itm){
        
        DefaultTableModel modelIndicadores = (DefaultTableModel) tabelaRelatorios.getModel();
        modelIndicadores.setNumRows(0);
        
        int anoCont = crm.getAnoIni();
        int anoFim  = crm.getAnoFim();
        int mesCont = crm.getMesIni() - 1;
        int mesFim  = crm.getMesFim();
        Object[] temp = new Object[32];
        
        modelIndicadores.addColumn("Indicadores", getITM());
        tabelaRelatorios.getColumnModel().getColumn(0).setPreferredWidth(300);
        
        do{
            temp[1] = "teste " + mesCont;
            
            mesCont++;
            
            if( mesCont > 12 ){
                mesCont = 1;
                anoCont++;
            }
            
            //----FINAL---------------------------------------------------------
          
            modelIndicadores.addColumn(mesCont + "/" + anoCont, temp);
          
            Util.clearVector(temp);
        }while(anoCont != anoFim || mesCont != mesFim);
        
    }
    
    public Object[] getIEM(){
        return new Object[] {
            "Renda bruta do leite (R$/Mês)",
            "Preço médio mensal do leite (R$/L)",
            "COE do leite (R$/Mês)",
            "COT do leite (R$/Mês)",
            "Custo total do leite (R$/Mês)",
            "COE unitário do leite (R$/L)",
            "COT unitário do leite (R$/L)",
            "CT unitário do leite (R$/Mês)",
            "COE do leite/preço do leite (L/Mês)",
            "COT do leite/preço do leite (L/Mês)",
            "CT do leite/preço do leite (L/Mês)",
            "Gasto com MDO contratada permanente do leite/renda bruta do leite (%)",
            "Gasto com MDO total do leite/ renda bruta do leite (%)",
            "Gasto com concentrado do leite/renda bruta do leite (%)",
            "Margem bruta do leite (R$/Mês)",
            "Margem bruta unitária (R$/L)",
            "Margem bruta em equivalente litros de leite (L/Mês)",
            "Margem bruta/Área (R$/ha/Mês)",
            "Margem bruta/vaca em lactação (R$/Cab)",
            "Margem bruta/total de vacas (R$/Cab)",
            "Margem líquida do leite (R$/Mês)",
            "Margem líquida unitária (R$/L)",
            "Margem líquida em equivalente litros de leite (L/Mês)", 
            "Margem líquida/Área (R$/ha/Mês)",
            "Lucro total do leite (R$/Mês)",
            "Lucro unitário (R$/L)",
            "Lucro em equivalente litros de leite (L/Mês)",
            "Custo da mão-de-obra familiar (R$/Mês)",
            "Lucratividade (% a.m.)",
            "Ponto de Resíduo ( RB = COT ) (L/dia)",
            "Ponto de Nivelamento ( RB = CT) - Litros/dia (L/dia)",
            "Capital investido por litro de leite (R$/L)"
        };
        
    }
    
    public Object[] getITM(){
        return new Object[] {
            "PRODUTIVOS",
            "Produção mensal de leite (L/dia)",
            "Área usada para pecuária (ha)",
            "Rebanho total",
            "Total de vacas (Cab)",
            "Vacas em lactação (Cab)",
            "Vaca Seca (Cab)",
            "Novilhas (Cab)",
            "Bezerras (Cab)",
            "Bezerros (Cab)",
            "Touro (Cab)",
            "Outros (Cab)",
            "Vacas em lactação / total de vacas (%)",
            "Vacas em lactação / rebanho (%)",
            "Vacas em lactação / área para pecuária (Cab/ha)",
            "Vacas em lactação / funcionário",
            "Produção / vaca em lactação (L/dia)",
            "Produção / mão-de-obra permanente (contratada) (L/dh)",
            "Produção / área para pecuária (1/2) x 365 (L/ha/Mês)",

            "SANITÁRIOS",
            "Nº Abortos",
            "Nº Natimortos",
            "Nº Retenção de placenta",
            "Nº Morte de bezerros",
            "Nº Bezerros doentes",
            "Nº Morte de novilhas",
            "Nº Morte de vacas",
            "Nº Vacas com mastite clínica",
        };    
    }
    
    public double getTotalLitroLeite(List<DadosTecMensais> dtm, int mes, int ano){
        for(int i = 0; i < dtm.size(); i++){
            if(dtm.get(i).getIndicador().getIndicador().equals("Total Litros/Mês (L")
                    && dtm.get(i).getMes() == mes && dtm.get(i).getAno()== ano){
                return dtm.get(i).getDado();
            }
        }
        
        return -1;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnVoltar = new javax.swing.JButton();
        textoEntrada = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaRelatorios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        textoEntrada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        textoEntrada.setForeground(new java.awt.Color(0, 38, 255));
        textoEntrada.setText("INDICADORES MENSAIS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        tabelaRelatorios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelaRelatorios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabelaRelatorios.setColumnSelectionAllowed(true);
        tabelaRelatorios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaRelatorios.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelaRelatorios);
        tabelaRelatorios.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(276, 276, 276)
                        .addComponent(textoEntrada)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 978, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar)
                    .addComponent(textoEntrada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        new MenuPrincipal().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVoltar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaRelatorios;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
