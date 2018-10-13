package com.tallerlenguajesii;

import com.tallerlenguajesii.missilecommand.CampoDeJuego;
import javax.swing.JFrame;

public class MissileCommand extends JFrame {

    public MissileCommand() {
        getContentPane().add(new CampoDeJuego());
        setSize(400, 350);
        setTitle("Misil Command");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }

    public static void main(String[] args) {
        MissileCommand missileCommand = new MissileCommand();
    }
}
