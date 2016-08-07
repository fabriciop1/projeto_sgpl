/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao.windows;

import com.sun.glass.events.KeyEvent;
import controle.ControlePerfil;
import flex.db.GenericDAO;
import flex.table.GenericTableAreaEditor;
import flex.table.TableModifiedEvent;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;
import modelo.negocio.DadosEconMensais;
import modelo.negocio.Especificacao;
import modelo.negocio.InventarioResumo;
import modelo.negocio.Perfil;
import util.Cast;
import util.ColorRendererDadosEcon;

/**
 *
 * @author Alexandre
 */
public class VisualizarDadosEconMensais extends javax.swing.JFrame {

    private final List<Especificacao> especificacoes;    
    private List<DadosEconMensais> dems;
    private final GenericDAO<DadosEconMensais> demdao;
    private final GenericDAO<Especificacao> demespdao;
    private final GenericTableAreaEditor gtae;
    private final Perfil atual;
    private double salarioMensal;
    private int page;
    
    /**
     * Creates new form VisualizarDadosEconMensais
     */
    public VisualizarDadosEconMensais() {
        
        initComponents();
       
        setPage(1);
        
        atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setTitle("SGPL - " + atual.getNome() + " - Dados Econômicos Mensais");
        
        this.setSalarioMensal();
        tabelaTrimestre.getTableHeader().setFont(super.getFont().deriveFont(Font.BOLD));
               
        tabelaDEM.setShowGrid(true);
        tabelaDEM.setDefaultRenderer(Object.class, new ColorRendererDadosEcon(true));
        
        demdao = new GenericDAO<>(DadosEconMensais.class);
        demespdao = new GenericDAO<>(Especificacao.class);
        
        especificacoes = demespdao.retrieveAll();
       
        gtae = new GenericTableAreaEditor(this, tabelaDEM, false);
                  
        fillComboBox();
        
        initGTAE();
        
        definirBDListeners();
        
    }
    
    private Object[] PreencherColunaESP(List<Especificacao> esp){
        
        Object[] colunaESP = new Object[90];

        int cont = 0;
        
        for(int i = 0; i < esp.size(); i++){
            
            if(i == 0)  { colunaESP[cont] = "A - ENTRADAS"; cont += 1; }
            if(i == 6)  { colunaESP[cont] = "TOTAL DE ENTRADAS"; cont += 1;
                          colunaESP[cont] = "B - SAÍDA"; cont += 1; }
            if(i == 17) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "DESPESAS COM VOLUMOSO"; cont += 1; }
            if(i == 28) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "CONCENTRADO"; cont += 1; }
            if(i == 38) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "MINERALIZAÇÃO"; cont += 1; }
            if(i == 44) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "MEDICAMENTOS"; cont += 1; }
            if(i == 56) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "ORDENHA"; cont += 1; }
            if(i == 60) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "INSEMINAÇÃO ARTIFICIAL"; cont += 1; }
            if(i == 65) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "DESPESAS DE INVESTIMENTO"; cont += 1; }
            if(i == 68) { colunaESP[cont] = "SUB-TOTAL"; cont += 1;
                          colunaESP[cont] = "DESPESAS DE EMPRÉSTIMOS"; cont += 1; }
            if(i == 69) { colunaESP[cont] = "COE DE ATIVIDADE LEITEIRA"; cont += 1; }
            
            colunaESP[cont] = esp.get(i).getEspecificacao();
            cont += 1;
        }
        
        return colunaESP;
        
    }
    
    private Object[][] addColunas(List<DadosEconMensais> dem, int mes){
        
        Object[][] colunas = new Object[3][90];
        Object[] esp = PreencherColunaESP(especificacoes);
        double[] tempTotais = new double[3];
        double coeAtivLeite = 0;
        
        
        for(int i = 0; i < esp.length; i++){
            
            for(int j = 0; j < dem.size(); j++){
                    
                if(mes == dem.get(j).getMes()){
                        if( esp[i].equals(dem.get(j).getEspecificacao().getEspecificacao())){

                            colunas[0][i] = dem.get(j).getQuantidade();
                            colunas[1][i] = dem.get(j).getValorUnitario();
                            colunas[2][i] = dem.get(j).getQuantidade() * dem.get(j).getValorUnitario();

                            tempTotais[0] += dem.get(j).getQuantidade();
                            tempTotais[2] += dem.get(j).getQuantidade() * dem.get(j).getValorUnitario();

                        }

                        if( esp[i].equals("SUB-TOTAL") || i == 87) {    
                            if (tempTotais[2] != 0.0) {
                                colunas[2][i] = tempTotais[2];
                                coeAtivLeite += tempTotais[2];
                                tempTotais[2] = 0.0;
                            } 
                        } 

                        if(i == 7) {

                            if (tempTotais[0] != 0.0) { 
                                colunas[0][i] = (int) tempTotais[0];
                                tempTotais[0] = 0.0;
                            }

                            if (tempTotais[2] != 0.0) {
                                colunas[2][i] = tempTotais[2];
                                tempTotais[2] = 0.0;
                            } 

                        }

                        if( i == 88 ){

                            if (coeAtivLeite != 0.0) {
                                colunas[2][i] = coeAtivLeite;
                                coeAtivLeite = 0.0;
                            } 

                        }

                        if( i == 89 ){
                            if (colunas[0] != null && this.getSalarioMensal() != 0.0) {
                                colunas[1][i] = this.getSalarioMensal();
                            }

                            if (colunas[0][i] != null && this.getSalarioMensal() != 0.0) {
                                colunas[2][i] = Double.parseDouble(colunas[0][i].toString()) * 
                                        Double.parseDouble(colunas[1][i].toString());
                            }
                        }
                    }
                }
                
        }
    
        return colunas;
        
    }
    
    private void PreencherTabelaDEM(List<DadosEconMensais> dem, List<Especificacao> esp) {
        
        Object[][] meses = new Object[3][2];
        Object[][] colunas;
        
        int paginaAtual = getPage();
        
        switch(paginaAtual){
            case 1:
                meses = new Object[][] {{ 1, "Janeiro"}, { 2, "Fevereiro"}, { 3,    "Março"}};
                break;
            case 2:
                meses = new Object[][] {{ 4,   "Abril"}, { 5,      "Maio"}, { 6,    "Junho"}};
                break;
            case 3:
                meses = new Object[][] {{ 7,   "Julho"}, { 8,    "Agosto"}, { 9, "Setembro"}};
                break;
            case 4:
                meses = new Object[][] {{10, "Outubro"}, {11,  "Novembro"}, {12, "Dezembro"}};
                break;
        }
        
        DefaultTableModel modelDEM = (DefaultTableModel) tabelaDEM.getModel();
        
        tabelaTrimestre.getColumnModel().getColumn(0).setHeaderValue(meses[0][1]);
        tabelaTrimestre.getColumnModel().getColumn(1).setHeaderValue(meses[1][1]);
        tabelaTrimestre.getColumnModel().getColumn(2).setHeaderValue(meses[2][1]);
        tabelaTrimestre.getTableHeader().resizeAndRepaint();
        
        DefaultTableModel modelDEMs = (DefaultTableModel) tabelaDEM.getModel();
        modelDEMs.addColumn("Especificação", PreencherColunaESP(esp));
                
        int mesInicio = Integer.parseInt(meses[0][0].toString());
        int mesFim    = Integer.parseInt(meses[2][0].toString());
                
        System.out.println(mesInicio + " " + mesFim);
        
        for(int i = mesInicio; i <= mesFim; i++){
            colunas = addColunas(dem, i);
            
            modelDEM.addColumn("Quant.",      colunas[0]);
            modelDEM.addColumn("Valor Unit.", colunas[1]);
            modelDEM.addColumn("Total (R$)",  colunas[2]);
        }
            
        tabelaDEM.setModel(modelDEM);
        tabelaDEM.getColumnModel().getColumn(0).setPreferredWidth(273);
        
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnVoltar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        anoCombo = new javax.swing.JComboBox<String>();
        adicionarAnoBT = new javax.swing.JButton();
        textoEntrada = new javax.swing.JLabel();
        avancarBT = new javax.swing.JButton();
        retornarBT = new javax.swing.JButton();
        editarValoresBT = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaTrimestre = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaDEM = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });

        btnVoltar.setText("Voltar");
        btnVoltar.setToolTipText("Menu Principal");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Ano:");

        anoCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                anoComboItemStateChanged(evt);
            }
        });

        adicionarAnoBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/add.png"))); // NOI18N
        adicionarAnoBT.setToolTipText("Adicionar novo ano");
        adicionarAnoBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarAnoBTActionPerformed(evt);
            }
        });

        textoEntrada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        textoEntrada.setForeground(new java.awt.Color(0, 38, 255));
        textoEntrada.setText("DADOS ECONÔMICOS MENSAIS");

        avancarBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/right_arrow.png"))); // NOI18N
        avancarBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avancarBTActionPerformed(evt);
            }
        });

        retornarBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/left_arrow.png"))); // NOI18N
        retornarBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retornarBTActionPerformed(evt);
            }
        });

        editarValoresBT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit.png"))); // NOI18N
        editarValoresBT.setToolTipText("Inserir/Editar dados");
        editarValoresBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarValoresBTActionPerformed(evt);
            }
        });

        tabelaTrimestre.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "mes1", "mes2", "mes3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaTrimestre.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelaTrimestre);
        if (tabelaTrimestre.getColumnModel().getColumnCount() > 0) {
            tabelaTrimestre.getColumnModel().getColumn(0).setResizable(false);
            tabelaTrimestre.getColumnModel().getColumn(1).setResizable(false);
            tabelaTrimestre.getColumnModel().getColumn(2).setResizable(false);
        }

        tabelaDEM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }

        )   {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        tabelaDEM.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tabelaDEM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(avancarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(textoEntrada)
                                .addGap(69, 69, 69)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(anoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adicionarAnoBT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editarValoresBT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(editarValoresBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(adicionarAnoBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                        .addComponent(textoEntrada))
                                    .addGap(1, 1, 1))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(retornarBT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(avancarBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(anoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        
        new MenuPrincipal().setVisible(true);
        this.setVisible(false);
        this.dispose();
        
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void editarValoresBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarValoresBTActionPerformed
        MonthSelector telaMes = new MonthSelector(this, true);
        telaMes.setTitle("SGPL - DEM - Seleção de Mês");

        telaMes.setVisible(true);
        int selecionado = telaMes.getSelected();
        
        if (selecionado > 0) {
            gtae.setLabelText("Editar - " + telaMes.getMonthSelected().toUpperCase());
       
            gtae.setTitle("Editar Dados Econômicos Mensais - " + telaMes.getMonthSelected().toUpperCase());

            configGTAE(selecionado);

            gtae.showEditor(evt);          
        }
    }//GEN-LAST:event_editarValoresBTActionPerformed

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        
    }//GEN-LAST:event_formMouseWheelMoved

    private void avancarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avancarBTActionPerformed
                
        boolean avancar = false;
        
        int pagAtual = getPage();
        
        if(pagAtual <= 3){
            pagAtual += 1;
            setPage(pagAtual);
            avancar = true;
        }
                
        if(avancar){
            PreencherTabelaDEM(dems, especificacoes);
        }
        
    }//GEN-LAST:event_avancarBTActionPerformed

    private void retornarBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retornarBTActionPerformed
    
        boolean retornar = false;
        
        int pagAtual = getPage();
        
        if(pagAtual > 1){
            pagAtual -= 1;
            setPage(pagAtual);
            retornar = true;
        }
        
        if(retornar){
            PreencherTabelaDEM(dems, especificacoes);
        }
        
    }//GEN-LAST:event_retornarBTActionPerformed

    private void adicionarAnoBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarAnoBTActionPerformed
        boolean existe = false;
        
        SingleYearSelector telaNovoAno = new SingleYearSelector(this, true);
        telaNovoAno.setTitle("SGPL - DEM - Seleção de ano");
        telaNovoAno.setVisible(true);
        
        String ano = telaNovoAno.getSelected();
        
        if(ano == null) {
            return;
        }
        
        for(int i = 0; i < anoCombo.getItemCount(); i++) {
            if (anoCombo.getItemAt(i).equals(ano)) {
                existe = true;
                break;
            }
        }
        if (existe) {
            JOptionPane.showMessageDialog(this, "Este ano já foi inserido para o perfil de " + atual.getNome()
                    + ".", "Alerta - Inserção de ano já cadastrado", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        
        anoCombo.addItem(ano);
        anoCombo.setSelectedItem(ano);
        
    }//GEN-LAST:event_adicionarAnoBTActionPerformed

    private void anoComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_anoComboItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
           dems = demdao.retrieveByColumns(new String[]{"idPerfilFK", "ano"}, new Object[]{atual.getId(), 
                                    Integer.parseInt(anoCombo.getSelectedItem().toString())});    
        PreencherTabelaDEM(dems, especificacoes);
        }    
    }//GEN-LAST:event_anoComboItemStateChanged

    private void fillComboBox() {
         
        List<DadosEconMensais> dados = demdao.retrieveByColumn("idPerfilFK", atual.getId(), "ano", "ano DESC");
        
        if (dados.isEmpty()) {
            Calendar cal = GregorianCalendar.getInstance();
            anoCombo.addItem(Cast.toString(cal.get(Calendar.YEAR)));
        }
        
        for(int i = 0; i < dados.size(); i++) {
            anoCombo.addItem(Cast.toString(dados.get(i).getAno()));
        }
        
    }
    
    private void definirBDListeners() {
        
        gtae.addTableModifyListener((TableModifiedEvent evt) -> {
       
            Object[][] areaData = evt.getTableAreaData();
            List<Integer> linhas = evt.getRowsModified();
            
            if(areaData == null){
                System.out.println("areaData is NULL in GTAE \"" + gtae.getName() + "\"");
            }
            
            for (Integer l : linhas) {

                Especificacao espec = demespdao.retrieveByColumn("especificacao", tabelaDEM.getValueAt(l, 0)).get(0);
                int ano = Integer.parseInt(anoCombo.getSelectedItem().toString());
                int mes = ( (gtae.getStartColumn() + 1) / 3 ) + 1;
                int quantidade = 0;
                double valorUnitario = 0.0;
                
                List<DadosEconMensais> dadosEM = demdao.retrieveByColumns(new String[]{"mes", "ano", "idDEM_especificacaoFK", "idPerfilFK"}, 
                                                                    new Object[]{mes, ano, espec.getId(), atual.getId()});

                if (!Cast.toString(areaData[l][0]).isEmpty()) {

                    quantidade = (Integer.valueOf(areaData[l][0].toString()));
                } 

                if (!Cast.toString(areaData[l][1]).isEmpty()) {

                    valorUnitario = (Double.parseDouble(areaData[l][1].toString()));
                } 

                if(dadosEM.isEmpty()){

                    DadosEconMensais dem = new DadosEconMensais(mes, ano, quantidade, valorUnitario, espec, atual.getId());

                    demdao.insert(dem);

                } else {

                    DadosEconMensais dem = dadosEM.get(0);
                    
                    if (Cast.toString(areaData[l][0]).isEmpty() && Cast.toString(areaData[l][1]).isEmpty()) {
                        demdao.remove(dem.getId());
                        continue;
                    }
                    
                    dem.setValorUnitario(valorUnitario);
                    dem.setQuantidade(quantidade);

                    demdao.update(dem);
                }
            }
            
            dems = demdao.retrieveByColumns(new String[]{"idPerfilFK", "ano"}, new Object[]{atual.getId(),
                    Integer.parseInt(anoCombo.getSelectedItem().toString())});
            PreencherTabelaDEM(dems, especificacoes);
            
        });
    }
    
    public double getSalarioMensal() {
        return salarioMensal;
    }
    
    private void setSalarioMensal() {
        double salarioMin, salarioMes = 0.0;
        
        GenericDAO<InventarioResumo> irdao = new GenericDAO<>(InventarioResumo.class);
        
        List<InventarioResumo> ir = irdao.retrieveByColumn("idPerfilFK", atual.getId());
        
        if (ir.size() > 0) {
             salarioMin = ir.get(0).getSalarioMinimo();
             salarioMes = (salarioMin * 13 + salarioMin * 0.3) / 12;
        }
        
        this.salarioMensal = salarioMes;
    }
            
    private void initGTAE(){
        
        List<String> especColumnRows = new ArrayList<>(tabelaDEM.getRowCount());
        
        for(int i=0; i<tabelaDEM.getRowCount(); i++){
            
            especColumnRows.add(Cast.toString(tabelaDEM.getValueAt(i, 0)));
        }
        
        gtae.setName("GTAE DadosEconMensais");
        
        gtae.addStringColumn(tabelaDEM.getColumnModel().getColumn(0).getWidth(), "Especificação", especColumnRows,
                        tabelaDEM.getDefaultRenderer(Object.class));
       
        gtae.setDefaultRenderer( tabelaDEM.getDefaultRenderer(Object.class) );
    }
    
    private void configGTAE(int selected) {
       
        List<Integer> rowsNotEditable = Arrays.asList(0, 7, 8, 20, 21, 33, 34, 45, 46, 53, 54, 67, 68, 73, 74, 80, 81, 85, 86, 88); 
        
        gtae.setRowsDisplayed(21);
        gtae.setAllowEmptyRows(true);
        
        gtae.setColumnInterval(((selected-1) * 3), ((selected-1)*3) + 2);
        
        gtae.setRowInterval(0, tabelaDEM.getRowCount()-1); //Linha 1 a 90
        
        //Configura o editor de acordo com o intervalo de linhas e colunas especificado.
        gtae.processEditor();
       
        /*setCellEditable, setColumnEditable e setRowEditable sobrescrevem uns aos outros.
         Ex: setCellEditable(false,0,0); == cell 0,0 is not editable
         setColumnEditable(true,0); == cell 0,0 is now editable (afeta todas as linhas, naquela coluna)
         setRowEditable(false,0); == cell 0,0 is not editable (again).  */
        
        //setColumnEditable afeta todas as células da coluna especificada, em todas linhas do GTAE 
        
        for (int i: rowsNotEditable) {
            gtae.setRowEditable(i, false);
        }
        
        gtae.setColumnEditable(2, false);
        
        gtae.setCellEditable(89, 1, false);
        
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionarAnoBT;
    private javax.swing.JComboBox<String> anoCombo;
    private javax.swing.JButton avancarBT;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton editarValoresBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton retornarBT;
    private javax.swing.JTable tabelaDEM;
    private javax.swing.JTable tabelaTrimestre;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
