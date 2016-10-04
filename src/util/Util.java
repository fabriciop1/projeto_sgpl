/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import controle.ControlePerfil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.YearMonth;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Fabricio
 */
public final class Util {
    
    private Util (){ }
        
    public static int diasDoMes(int ano, int mes){
        
        YearMonth yearmonth = YearMonth.of(ano, mes);
        return yearmonth.lengthOfMonth();
        
    }
    
    public static Object[] clearVector(Object[] vector) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] = null; 
        }
        return vector;
    }
    
    public static String nomeMes(int mesNum){
        
        switch(mesNum){
            case 1:  return "JAN";
            case 2:  return "FEV";
            case 3:  return "MAR";
            case 4:  return "ABR";
            case 5:  return "MAI";
            case 6:  return "JUN";
            case 7:  return "JUL";
            case 8:  return "AGO";
            case 9:  return "SET";
            case 10: return "OUT";
            case 11: return "NOV";
            case 12: return "DEZ";
        }
        
        return "";
    }
    
    public static void CSVWriter(JTable table, String title) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Selecione uma pasta para salvar o arquivo");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos CSV", "csv");
        fc.setFileFilter(filtro);
        fc.setAcceptAllFileFilterUsed(false);
        
        int retorno = fc.showSaveDialog(table.getParent().getParent());
        
        if (retorno == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            try {
            
                OutputStream os = new FileOutputStream(file.getPath() + "\\" + ControlePerfil.getInstance().getPerfilSelecionado().getNome()
                        + "_" + ControlePerfil.getInstance().getAno() + "_" + title + ".csv");
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(os , "CP1252"));
                StringBuilder sb = new StringBuilder();
                
                for (int i = 0; i < model.getColumnCount() / 2 - 1; i++) {
                    sb.append(';');
                }
                
                sb.append(title + " - " + ControlePerfil.getInstance().getPerfilSelecionado().getNome() + " " + ControlePerfil.getInstance().getAno());
                sb.append('\n');
                sb.append('\n');
                
                for(int i = 0; i < model.getColumnCount(); i++) {
                    sb.append(model.getColumnName(i));
                    sb.append(';');
                }

                sb.append('\n');
                sb.append('\n');

                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        if (model.getValueAt(i, j) == null || model.getValueAt(i, j).toString().isEmpty()) {
                            sb.append(" ");
                            sb.append(';');
                            continue;
                        }
                        sb.append(Cast.toBRLocaleValue(model.getValueAt(i, j)));
                        sb.append(';');
                    }
                    sb.append('\n');
                }

                pw.write(sb.toString());
                pw.close();
                
                JOptionPane.showMessageDialog(table.getParent().getParent(), "Exportação realizada com sucesso.", "Exportação de dados para Excel",
                                    JOptionPane.INFORMATION_MESSAGE);
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(table.getParent().getParent(), "Arquivo não salvo.", "Ocorreu um problema com o arquivo.\n"
                        + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            } 
        }    
    }
}
