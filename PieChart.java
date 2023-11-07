package com.javavisuals.visual;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;

import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PieChart {
    private JPanel mainFrame;
    private JTextField item;
    private JTextField value;
    private JPanel piePanel;
    private JPanel tablePanel;
    private JButton CREATEButton;
    private JButton RESETButton;
    private JButton ADDDATAButton;
    private JButton REMOVEDATAButton;
    private JRadioButton pieChartRadioButton;
    private JRadioButton barGraphRadioButton;
    private DefaultPieDataset pieDataset;
    private JFreeChart pieChart;
    private DefaultCategoryDataset barDataset;
    private JFreeChart barChart;
    private ChartPanel chartPanel;
    DefaultTableModel model;
    JTable table;
    JFrame frame = new JFrame();
    public PieChart() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainFrame);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //displayTable();
        String[] a = {"Entities", "Value"};
        model = new DefaultTableModel(null,a);
        table = new JTable(model);
        JScrollPane Table = new JScrollPane(table);
        tablePanel.add(Table);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        ADDDATAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = item.getText();
                String amountData = value.getText();
                if (!itemName.isEmpty() && !amountData.isEmpty()){
                    Object[] data = {itemName, amountData};
                    model.addRow(data);
                    item.setText("");
                    value.setText("");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Both Entity Name and Value must be filled in.");
                }

            }
        });

        CREATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                piePanel.removeAll();
                if (pieChartRadioButton.isSelected()){
                    showPie();
                }
                else if (barGraphRadioButton.isSelected()){
                    showBar();
                }
                frame.validate();
            }
        });

        RESETButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                piePanel.removeAll();
                tablePanel.removeAll();
                pieChartRadioButton.setSelected(false);
                barGraphRadioButton.setSelected(false);
                displayTable();
                frame.validate();
            }
        });

        REMOVEDATAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow() != -1){
                    model.removeRow(table.getSelectedRow());
                }
            }
        });

        pieChartRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                barGraphRadioButton.setSelected(false);
            }
        });

        barGraphRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pieChartRadioButton.setSelected(false);
            }
        });
    }

    public void displayTable(){
        String[] a = {"Entities", "Values"};
        model = new DefaultTableModel(null,a);
        table = new JTable(model);
        JScrollPane Table = new JScrollPane(table);
        tablePanel.add(Table);
    }

    public void showPie(){
        pieDataset = new DefaultPieDataset();
        for(int i=0;i<table.getRowCount();i++){
            String name = table.getValueAt(i,0).toString();
            Double amt = Double.valueOf(table.getValueAt(i,1).toString());
            pieDataset.setValue(name,amt);
        }
        pieChart = ChartFactory.createPieChart3D("PIE CHART",pieDataset,true,true,true);
        pieChart.getPlot();
        chartPanel = new ChartPanel(pieChart);
        piePanel.add(chartPanel);
    }

    public void showBar(){
        barDataset = new DefaultCategoryDataset();
        for(int i = 0; i < table.getRowCount(); i++){
            String name = table.getValueAt(i,0).toString();
            Double amt = Double.valueOf(table.getValueAt(i,1).toString());
            barDataset.addValue(amt, "Data", name);
        }

        barChart = ChartFactory.createBarChart("BAR CHART", "Entities", "Value", barDataset,
                PlotOrientation.VERTICAL,true, true, false);
        chartPanel = new ChartPanel(barChart);
        piePanel.add(chartPanel);
    }

}
