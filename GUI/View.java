package GUI;

import logic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JPanel{
    private Boolean simStarted = true;
    JPanel panel1 = new JPanel();
    JPanel panel0 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel panel5 = new JPanel();
    JPanel panel6 = new JPanel();
    JPanel panel7 = new JPanel();
    JPanel panel8 = new JPanel();
    JTextField nrClients = new JTextField("Write the number of clients");
    JTextField nrQueues = new JTextField("Write the number of queues");
    JTextField simulationTime = new JTextField("Write the simulation time in seconds");
    JTextField minArrivalTime = new JTextField("Write the minimum arrival time in seconds");
    JTextField maxArrivalTime = new JTextField("Write the maximum arrival time in seconds");
    JTextField minServiceTime = new JTextField("Write the minimum service time in seconds");
    JTextField maxServiceTime = new JTextField("Write the maximum service time in seconds");
    JButton opButton = new JButton("Start simulation");
    JFrame frame = new JFrame("Queues management");
    String[] options = {"Queue Strategy", "Time Strategy"};
    JComboBox<String> dropdown = new JComboBox<>(options);
    JTextArea output = new JTextArea();
    public View() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 600);
        panel0.setLayout(new FlowLayout());
        dropdown.setSelectedIndex(0); // Set default selection
        opButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) dropdown.getSelectedItem();
            }
        });
        nrClients.setPreferredSize(new Dimension(400, 50));
        nrQueues.setPreferredSize(new Dimension(400, 50));
        simulationTime.setPreferredSize(new Dimension(400, 50));
        minArrivalTime.setPreferredSize(new Dimension(400, 50));
        maxArrivalTime.setPreferredSize(new Dimension(400, 50));
        minServiceTime.setPreferredSize(new Dimension(400, 50));
        maxServiceTime.setPreferredSize(new Dimension(400, 50));
        output.setPreferredSize(new Dimension(400, 200));
        opButton.addActionListener(e -> vals());
        panel0.add(nrClients);
        panel1.add(nrQueues);
        panel0.add(simulationTime);
        panel3.add(minArrivalTime);
        panel4.add(maxArrivalTime);
        panel5.add(minServiceTime);
        panel6.add(maxServiceTime);
        panel7.add(opButton); panel7.add(dropdown); panel8.add(output);
        JPanel panel = new JPanel();
        panel.add(panel0);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.add(panel5);
        panel.add(panel6);
        panel.add(panel7);
        panel.add(panel8);
        frame.setContentPane(panel); frame.setVisible(true);
    }

    public void printInView(String str){
        output.setText(str);
    }

    public boolean isNumeric(String str) {
        return str.chars().allMatch(Character::isDigit);
    }
    public boolean check(){
        if(!isNumeric(nrClients.getText())){
            return false;
        }
        if(!isNumeric(nrQueues.getText())){
            return false;
        }
        if(!isNumeric(simulationTime.getText())){
            return false;
        }
        if(!isNumeric(minArrivalTime.getText())){
            return false;
        }
        if(!isNumeric(maxArrivalTime.getText())){
            return false;
        }
        if(!isNumeric(minServiceTime.getText())){
            return false;
        }
        if(!isNumeric(maxServiceTime.getText())){
            return false;
        }
        return true;
    }

    public void vals(){
        if(!simStarted){
            JOptionPane.showMessageDialog(null, "Already simulating", null, JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(check() == false){
            JOptionPane.showMessageDialog(null, "Wrong input format", null, JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer arrMin = Integer.parseInt(minArrivalTime.getText());
        Integer arrMax = Integer.parseInt(maxArrivalTime.getText());
        if(arrMin > arrMax){
            JOptionPane.showMessageDialog(null, "Wrong input format", null, JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer serviceMin = Integer.parseInt(minServiceTime.getText());
        Integer serviceMax = Integer.parseInt(maxServiceTime.getText());
        if(serviceMax < serviceMin){
            JOptionPane.showMessageDialog(null, "Wrong input format", null, JOptionPane.WARNING_MESSAGE);
            return;
        }
        SimulationManager.start(this);
        simStarted = false;
    }

    public Integer getNrClients() {
        return Integer.valueOf(nrClients.getText());

    }

    public Integer getNrQueues() {
        return Integer.valueOf(nrQueues.getText());
    }

    public Integer getSimulationTime() {
        return Integer.valueOf(simulationTime.getText());
    }

    public Integer getMinArrivalTime() {
        return Integer.valueOf(minArrivalTime.getText());
    }

    public Integer getMaxArrivalTime() {
        return Integer.valueOf(maxArrivalTime.getText());
    }

    public Integer getMinServiceTime() {
        return Integer.valueOf(minServiceTime.getText());
    }

    public Integer getMaxServiceTime() {
        return Integer.valueOf(maxServiceTime.getText());
    }
    public String getSelectedStrategy(){
        return (String) dropdown.getSelectedItem();
    }
    public void setVisibility(boolean isVisible){
        frame.setVisible(isVisible);
    }

    public void showError(String message){
        JOptionPane.showMessageDialog(frame, message, "Swing Tester", JOptionPane.ERROR_MESSAGE);
    }
}

