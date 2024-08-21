package com.frota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.back.Caminhao;
import com.back.dataBaseManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class checkPage {

    @FXML
    private TableView<Caminhao> tabelaCaminhoes;

    @FXML
    private TableColumn<Caminhao, Integer> colunaId;

    @FXML
    private TableColumn<Caminhao, String> colunaMarca;

    @FXML
    private TableColumn<Caminhao, Integer> colunaAno;

    @FXML
    private TableColumn<Caminhao, Integer> colunaValor;

    @FXML
    private TableColumn<Caminhao, String> colunaTipo;

    @FXML
    private TableColumn<Caminhao, Double> colunaCarroceria;

    @FXML
    private TableColumn<Caminhao, Integer> colunaCarga;

    @FXML
    private Button deleteButton;

    @FXML
    public void initialize() {
        if (tabelaCaminhoes == null || deleteButton == null) {
            System.err.println("Erro ao carregar alguns componentes do FXML. Verifique o arquivo check.fxml.");
            return;
        }

        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colunaAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colunaCarroceria.setCellValueFactory(new PropertyValueFactory<>("carroceria"));
        colunaCarga.setCellValueFactory(new PropertyValueFactory<>("carga"));

        ObservableList<Caminhao> caminhoes = getCaminhoes();
        if (caminhoes.isEmpty()) {
            System.out.println("Nenhum dado encontrado no banco de dados.");
        } else {
            System.out.println("Dados carregados com sucesso.");
        }
        tabelaCaminhoes.setItems(caminhoes);

        deleteButton.setDisable(true);

        tabelaCaminhoes.setRowFactory(tv -> new TableRow<Caminhao>() {
            @Override
            protected void updateItem(Caminhao item, boolean empty) {
                super.updateItem(item, empty);
                setOnMouseClicked(event -> {
                    if (event.getClickCount() == 1 && !isEmpty()) {
                        deleteButton.setDisable(false);
                    } else {
                        deleteButton.setDisable(true);
                    }
                });
            }
        });
    }

    private ObservableList<Caminhao> getCaminhoes() {
        ObservableList<Caminhao> caminhoes = FXCollections.observableArrayList();
        String sql = "SELECT * FROM caminhoes";

        try (Connection conn = dataBaseManager.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String marca = rs.getString("marca");
                int ano = rs.getInt("ano");
                int valor = rs.getInt("valor");
                String tipo = rs.getString("tipo");
                Double carroceria = rs.getDouble("carroceria");
                int carga = rs.getInt("carga");
                Caminhao caminhao = new Caminhao(id, marca, ano, valor, tipo, carroceria, carga);
                caminhoes.add(caminhao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return caminhoes;
    }

    @FXML
    private void deletarClick() {
        Caminhao selectedCaminhao = tabelaCaminhoes.getSelectionModel().getSelectedItem();

        if (selectedCaminhao != null) {
            tabelaCaminhoes.getItems().remove(selectedCaminhao);
            deleteCaminhaoFromDatabase(selectedCaminhao.getId());
            deleteButton.setDisable(true);
            showAlert(AlertType.INFORMATION, "Deletar Caminhão", "Caminhão deletado com sucesso!");
        }
    }

    private void deleteCaminhaoFromDatabase(int id) {
        String sql = "DELETE FROM caminhoes WHERE id = ?";

        try (Connection conn = dataBaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void switchToMenu() {
        App.switchToMenuScene();
    }
}
