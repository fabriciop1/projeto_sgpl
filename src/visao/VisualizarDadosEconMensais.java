/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import controle.ControlePerfil;
import flex.db.GenericDAO;
import java.awt.Color;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;
import modelo.negocio.DadosEconMensais;
import modelo.negocio.Especificacao;
import modelo.negocio.Perfil;
import util.ColorRenderer;

/**
 *
 * @author Alexandre
 */
public class VisualizarDadosEconMensais extends javax.swing.JFrame {

    List<Especificacao> especificacoes;    
    List<DadosEconMensais> dems;
    GenericDAO<DadosEconMensais> demdao;
    GenericDAO<Especificacao> demespdao;        
     
    
    /**
     * Creates new form VisualizarDadosEconMensais
     */
    public VisualizarDadosEconMensais() {
        initComponents();
        
        setLocationRelativeTo(null);
        this.setResizable(false);
      
        Perfil atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        this.setTitle("SGPL - " + atual.getNome() + " - Dados Econômicos Mensais");
        
        Calendar cal = GregorianCalendar.getInstance();
	int anoAtual = cal.get(Calendar.YEAR);
        int mesAtual = cal.get(Calendar.MONTH);
        
        tabelaEspecificacao.setShowHorizontalLines(true);
        
        tabelaDadosEconomicos.setShowGrid(true);
        
        tabelaDadosEconomicos.setDefaultRenderer(Object.class, new ColorRenderer());
        tabelaEspecificacao.setDefaultRenderer(Object.class, new ColorRenderer());
        
        demdao = new GenericDAO<>(DadosEconMensais.class);
                
        demespdao = new GenericDAO<>(Especificacao.class);
        
        tabelaDadosEconomicos.setDefaultRenderer(Color.class, new ColorRenderer(true));
             
        especificacoes = demespdao.retrieveAll();
        
        PreencherTabelaESP(especificacoes);
        
        fillItemSelector();
                
        //PreencherTabelaDEM(anoAtual, dems);
    }
    
    private void PreencherTabelaESP(List<Especificacao> esp){
       
        
        DefaultTableModel modelEspecificacao = (DefaultTableModel) tabelaEspecificacao.getModel();
        modelEspecificacao.setNumRows(0);

        for(int i = 0; i < esp.size(); i++){
            
            if(i == 0)  { modelEspecificacao.addRow(new Object[]{ "A - ENTRADAS" }); }
            if(i == 6)  { modelEspecificacao.addRow(new Object[]{ "TOTAL DE ENTRADAS" });
                          modelEspecificacao.addRow(new Object[]{ "B - SAÍDA" }); }
            if(i == 17) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "DESPESAS COM VOLUMOSO" }); }
            if(i == 28) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" }); 
                          modelEspecificacao.addRow(new Object[]{ "CONCENTRADO" }); }
            if(i == 38) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "MINERALIZAÇÃO" }); }
            if(i == 44) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "MEDICAMENTOS" }); }
            if(i == 56) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "ORDENHA" }); }
            if(i == 60) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "INSEMINAÇÃO ASTIFICIAL" }); }
            if(i == 65) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "DESPESAS DE INVESTIMENTO" }); }
            if(i == 68) { modelEspecificacao.addRow(new Object[]{ "SUB-TOTAL" });
                          modelEspecificacao.addRow(new Object[]{ "DESPESAS DE EMPRÉSTIMOS" }); }
            if(i == 69) { modelEspecificacao.addRow(new Object[]{ "COE DE ATIVIDADE LEITEIRA" }); }
            
            modelEspecificacao.addRow( new Object[]{ esp.get(i).getEspecificacao() });
        }
    }
    
    private void PreencherTabelaDEM(int ano, List<DadosEconMensais> dem){
        
        int tipoEsp = 1; //Valor de 1 a 11;
        
        DefaultTableModel modelEspecificacao = (DefaultTableModel) tabelaEspecificacao.getModel();
        DefaultTableModel modelDadosEconomicos = (DefaultTableModel) tabelaDadosEconomicos.getModel();
        modelDadosEconomicos.setNumRows(0);
        
        Object[] temp;
        double[] tempTotais = new double[36];
        
        for(int i = 0; i < modelEspecificacao.getRowCount(); i++){
            
            temp = new Object[36];
            
            for(int j = 0; j < dem.size(); j++){
                
                int indexCol = (dem.get(j).getMes() - 1) * 3;
                
                System.out.println(tabelaEspecificacao.getModel().getValueAt(i, 0));
                if( tabelaEspecificacao.getModel().getValueAt(i, 0).equals(dem.get(j).getEspecificacao().getEspecificacao())
                        && dem.get(j).getAno() == ano
                         ){
                                      
                    temp[indexCol ] = dem.get(j).getQuantidade();
                    temp[indexCol + 1] = dem.get(j).getValorUnitario();
                    temp[indexCol + 2] = dem.get(j).getQuantidade() * dem.get(j).getValorUnitario();
                    
                    tempTotais[indexCol] += dem.get(j).getQuantidade();
                    tempTotais[indexCol + 2] += dem.get(j).getQuantidade() * dem.get(j).getValorUnitario();
                    
                }
                
                if( tabelaEspecificacao.getModel().getValueAt(i, 0).equals("SUB-TOTAL") || 
                    tabelaEspecificacao.getModel().getValueAt(i, 0).equals("TOTAL DE ENTRADAS")){
                                        
                    if (tempTotais[indexCol] != 0.0 && tabelaEspecificacao.getModel().getValueAt(i, 0).equals("TOTAL DE ENTRADAS")) { 
                        temp[indexCol] = tempTotais[indexCol];
                        tempTotais[indexCol] = 0.0;
                    }
                    
                    if (tempTotais[indexCol + 2] != 0.0) {
                        temp[indexCol + 2] = tempTotais[indexCol + 2];
                        tempTotais[indexCol + 2] = 0.0;
                    } 
                    
                    tipoEsp++;
                }
                
            }
            
            modelDadosEconomicos.addRow(temp);
            
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

        btnVoltar = new javax.swing.JButton();
        textoEntrada = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaEspecificacao = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaDadosEconomicos = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaMeses = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        anoCombo = new javax.swing.JComboBox<String>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        textoEntrada.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        textoEntrada.setForeground(new java.awt.Color(51, 153, 255));
        textoEntrada.setText("DADOS ECONÔMICOS MENSAIS");

        jScrollPane1.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jScrollPane1MouseWheelMoved(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(2700, 1515));

        tabelaEspecificacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Especificação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaEspecificacao.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabelaEspecificacao.setPreferredSize(new java.awt.Dimension(270, 1448));
        tabelaEspecificacao.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaEspecificacao.getTableHeader().setReorderingAllowed(false);
        tabelaEspecificacao.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tabelaEspecificacaoMouseDragged(evt);
            }
        });
        tabelaEspecificacao.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                tabelaEspecificacaoMouseWheelMoved(evt);
            }
        });
        tabelaEspecificacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabelaEspecificacaoMousePressed(evt);
            }
        });
        tabelaEspecificacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaEspecificacaoKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaEspecificacao);
        tabelaEspecificacao.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tabelaEspecificacao.getColumnModel().getColumnCount() > 0) {
            tabelaEspecificacao.getColumnModel().getColumn(0).setResizable(false);
            tabelaEspecificacao.getColumnModel().getColumn(0).setHeaderValue("Especificação");
        }

        jScrollPane3.setPreferredSize(new java.awt.Dimension(800, 1424));

        tabelaDadosEconomicos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabelaDadosEconomicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Quant.", "Valor Unit.", "Total (R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)", "Quant.", "Valor Unit.", "Total(R$)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaDadosEconomicos.setPreferredSize(new java.awt.Dimension(800, 1448));
        tabelaDadosEconomicos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaDadosEconomicos.getTableHeader().setResizingAllowed(false);
        tabelaDadosEconomicos.getTableHeader().setReorderingAllowed(false);
        tabelaDadosEconomicos.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tabelaDadosEconomicosMouseDragged(evt);
            }
        });
        tabelaDadosEconomicos.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                tabelaDadosEconomicosMouseWheelMoved(evt);
            }
        });
        tabelaDadosEconomicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabelaDadosEconomicosMousePressed(evt);
            }
        });
        tabelaDadosEconomicos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaDadosEconomicosKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaDadosEconomicos);
        if (tabelaDadosEconomicos.getColumnModel().getColumnCount() > 0) {
            tabelaDadosEconomicos.getColumnModel().getColumn(0).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(1).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(2).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(3).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(4).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(5).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(6).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(7).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(8).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(9).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(10).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(11).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(12).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(13).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(14).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(15).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(16).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(17).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(18).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(19).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(20).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(21).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(22).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(23).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(24).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(25).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(26).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(27).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(28).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(29).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(30).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(31).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(32).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(33).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(34).setResizable(false);
            tabelaDadosEconomicos.getColumnModel().getColumn(35).setResizable(false);
        }

        tabelaMeses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaMeses.setPreferredSize(new java.awt.Dimension(800, 0));
        tabelaMeses.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tabelaMeses);
        if (tabelaMeses.getColumnModel().getColumnCount() > 0) {
            tabelaMeses.getColumnModel().getColumn(0).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(1).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(2).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(3).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(4).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(5).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(6).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(7).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(8).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(9).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(10).setResizable(false);
            tabelaMeses.getColumnModel().getColumn(11).setResizable(false);
        }

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/visao/images/edit_values.png"))); // NOI18N
        jButton1.setText("Editar Valores");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 2420, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1480, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1480, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jScrollPane1.setViewportView(jPanel1);

        anoCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anoComboActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Ano:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(304, 304, 304)
                .addComponent(textoEntrada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(anoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar)
                    .addComponent(textoEntrada)
                    .addComponent(anoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed

        new MenuPrincipal().setVisible(true);
        this.setVisible(false);
        this.dispose();

    }//GEN-LAST:event_btnVoltarActionPerformed

    private void jScrollPane1MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jScrollPane1MouseWheelMoved
        moveScrollBar(evt);
    }//GEN-LAST:event_jScrollPane1MouseWheelMoved

    private void tabelaDadosEconomicosMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_tabelaDadosEconomicosMouseWheelMoved
        moveScrollBar(evt);
    }//GEN-LAST:event_tabelaDadosEconomicosMouseWheelMoved

    private void tabelaEspecificacaoMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_tabelaEspecificacaoMouseWheelMoved
        moveScrollBar(evt);
    }//GEN-LAST:event_tabelaEspecificacaoMouseWheelMoved

    private void tabelaEspecificacaoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaEspecificacaoMousePressed
       tabelaDadosEconomicos.setRowSelectionInterval(tabelaEspecificacao.getSelectedRow(), tabelaEspecificacao.getSelectedRow());
    }//GEN-LAST:event_tabelaEspecificacaoMousePressed

    private void tabelaDadosEconomicosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDadosEconomicosMousePressed
        tabelaEspecificacao.setRowSelectionInterval(tabelaDadosEconomicos.getSelectedRow(), tabelaDadosEconomicos.getSelectedRow());
    }//GEN-LAST:event_tabelaDadosEconomicosMousePressed

    private void tabelaDadosEconomicosMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaDadosEconomicosMouseDragged
        tabelaDadosEconomicosMousePressed(evt);
    }//GEN-LAST:event_tabelaDadosEconomicosMouseDragged

    private void tabelaEspecificacaoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaEspecificacaoMouseDragged
       tabelaEspecificacaoMousePressed(evt);
    }//GEN-LAST:event_tabelaEspecificacaoMouseDragged

    private void tabelaEspecificacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaEspecificacaoKeyPressed
        int temp = 0;
        if(evt.getKeyCode() == 40 && tabelaEspecificacao.getSelectedRow() != (tabelaEspecificacao.getRowCount() - 1)) {
            temp = tabelaEspecificacao.getSelectedRow() + 1;
            tabelaDadosEconomicos.setRowSelectionInterval(temp, temp);
        } else if (evt.getKeyCode() == 38 && tabelaEspecificacao.getSelectedRow() != 0){
            temp = tabelaEspecificacao.getSelectedRow() - 1;
            tabelaDadosEconomicos.setRowSelectionInterval(temp, temp);
        }
    }//GEN-LAST:event_tabelaEspecificacaoKeyPressed

    private void tabelaDadosEconomicosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaDadosEconomicosKeyPressed
        int temp = 0;
        if(evt.getKeyCode() == 40 && tabelaDadosEconomicos.getSelectedRow() != (tabelaDadosEconomicos.getRowCount() - 1)) {
            temp =  tabelaDadosEconomicos.getSelectedRow() + 1;
            tabelaEspecificacao.setRowSelectionInterval(temp, temp);
            
        } else if (evt.getKeyCode() == 38 &&  tabelaDadosEconomicos.getSelectedRow() != 0){
            temp =  tabelaDadosEconomicos.getSelectedRow() - 1;
            tabelaEspecificacao.setRowSelectionInterval(temp, temp);
        }
    }//GEN-LAST:event_tabelaDadosEconomicosKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        MonthSelector telaMes = new MonthSelector(this, true);
        
        telaMes.setVisible(true);
        int selecionado = telaMes.getIndex();
        System.out.println(selecionado);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void anoComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anoComboActionPerformed
        System.out.println(anoCombo.getItemAt(anoCombo.getSelectedIndex()));
      //  PreencherTabelaDEM(Integer.parseInt(anoCombo.getItemAt(anoCombo.getSelectedIndex())) , dems);
    }//GEN-LAST:event_anoComboActionPerformed

    private void fillItemSelector() {
        Perfil atual = ControlePerfil.getInstance().getPerfilSelecionado();
        
        List<DadosEconMensais> dados =  demdao.retrieveAll("ano", "ano");
        dems = demdao.retrieveByColumn("idPerfilFK", atual.getId());
        
        for(int i = 0; i < dados.size(); i++) {
            anoCombo.addItem("" + dados.get(i).getAno());
        }
        
        anoCombo.setSelectedIndex(anoCombo.getItemCount() - 1);
        
        PreencherTabelaDEM(Integer.parseInt( (String) anoCombo.getSelectedItem() ), dems);
    }
    
    private void moveScrollBar(java.awt.event.MouseWheelEvent evt) {
        JScrollBar bar = jScrollPane1.getVerticalScrollBar();
        if(evt.getWheelRotation() > 0) { //Up
            bar.setValue(bar.getValue()+25);
        } else { //Down
            bar.setValue(bar.getValue()-25);
        }     
    }
    
    private double[] zerarVetor( double[] vetor){
        
        for(int i = 0; i < vetor.length; i++){
            vetor[i] = 0.0;
        }
        
        return vetor;
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> anoCombo;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tabelaDadosEconomicos;
    private javax.swing.JTable tabelaEspecificacao;
    private javax.swing.JTable tabelaMeses;
    private javax.swing.JLabel textoEntrada;
    // End of variables declaration//GEN-END:variables
}
