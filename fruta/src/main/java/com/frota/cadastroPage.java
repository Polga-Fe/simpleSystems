package com.frota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.back.dataBaseManager;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class cadastroPage {

    @FXML
    private ToggleButton plataforma;

    @FXML
    private ToggleButton cegonha;

    @FXML
    private TextField marcaText;

    @FXML
    private TextField anoText;

    @FXML
    private TextField valorText;

    @FXML
    private TextField carroceriaText;

    @FXML
    private TextField cargaText;

    @FXML
    private VBox carroceriaVBox;

    @FXML
    private VBox cargaVBox;

    private ToggleGroup tipoCaminhaoGroup;

    @FXML
    public void initialize() {
        tipoCaminhaoGroup = new ToggleGroup();
        plataforma.setToggleGroup(tipoCaminhaoGroup);
        cegonha.setToggleGroup(tipoCaminhaoGroup);

        // Inicialmente, esconda os campos de carroceria e carga
        carroceriaVBox.setVisible(false);
        cargaVBox.setVisible(false);

        plataforma.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> updateFieldVisibility());
        cegonha.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> updateFieldVisibility());

        // Criar a tabela caso não exista
        dataBaseManager.createTableIfNotExists();
    }
    
    @FXML
    private void switchToMenu() {
        App.switchToMenuScene();
    }

    @FXML
    private void cadastroCaminhoes() {
        ToggleButton selectedToggleButton = (ToggleButton) tipoCaminhaoGroup.getSelectedToggle();
        if (selectedToggleButton != null) {
            String tipoCaminhao = selectedToggleButton.getText();
            String marca = marcaText.getText();
            String anoString = anoText.getText();
            String valorString = valorText.getText();
            String carroceriaString = carroceriaText.getText();
            String cargaString = cargaText.getText();

            try {
                int ano = Integer.parseInt(anoString);
                int valor = Integer.parseInt(valorString);
                double carroceria = carroceriaString.isEmpty() ? 0 : Double.parseDouble(carroceriaString);
                int carga = cargaString.isEmpty() ? 0 : Integer.parseInt(cargaString);

                // Ajustar os valores conforme o tipo de caminhão
                if (tipoCaminhao.equals("PLATAFORMA")) {
                    carga = 0;
                } else if (tipoCaminhao.equals("CEGONHA")) {
                    carroceria = 0;
                }

                // Inserir os dados no banco de dados
                insertTruckData(tipoCaminhao, marca, ano, valor, carroceria, carga);
            } catch (NumberFormatException e) {
                System.out.println("Ano ou valor inválido. Por favor, insira números válidos.");
            }
        } else {
            System.out.println("Nenhum tipo de caminhão foi selecionado.");
        }
    }

    private void updateFieldVisibility() {
        ToggleButton selectedToggleButton = (ToggleButton) tipoCaminhaoGroup.getSelectedToggle();
        if (selectedToggleButton != null) {
            String tipoCaminhao = selectedToggleButton.getText();
            if (tipoCaminhao.equals("PLATAFORMA")) {
                carroceriaVBox.setVisible(true);
                cargaVBox.setVisible(false);
            } else if (tipoCaminhao.equals("CEGONHA")) {
                carroceriaVBox.setVisible(false);
                cargaVBox.setVisible(true);
            }
        } else {
            carroceriaVBox.setVisible(false);
            cargaVBox.setVisible(false);
        }
    }

    private void insertTruckData(String tipo, String marca, int ano, int valor, double carroceria, int carga) {
        String sql = "INSERT INTO caminhoes(tipo, marca, ano, valor, carroceria, carga) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataBaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tipo);
            pstmt.setString(2, marca);
            pstmt.setInt(3, ano);
            pstmt.setInt(4, valor);
            pstmt.setDouble(5, carroceria);
            pstmt.setInt(6, carga);
            pstmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace(); // Melhor para debugging
        }
    }
}
