import javax.swing.JOptionPane;
import java.sql.*;

public class App {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Farmacia";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            while (true) {
                String menu = """
                        Escolha uma tabela para manipular:
                        1. Medicamentos
                        2. Clientes
                        3. Vendas
                        4. ItensVenda
                        0. Sair
                        """;
                String option = JOptionPane.showInputDialog(menu);
                if (option == null || option.equals("0")) {
                    JOptionPane.showMessageDialog(null, "Programa encerrado!");
                    break;
                }

                switch (option) {
                    case "1" -> menuMedicamentos(connection);
                    case "2" -> menuClientes(connection);
                    case "3" -> menuVendas(connection);
                    case "4" -> menuItensVenda(connection);
                    default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de conexão: " + e.getMessage());
        }
    }

    //Menu de Medicamentos
    public static void menuMedicamentos(Connection connection) throws SQLException {
        while (true) {
            String menu = """
                    Manipulação de Medicamentos:
                    1. Criar novo medicamento
                    2. Ler medicamentos
                    3. Atualizar medicamento
                    4. Deletar medicamento
                    0. Voltar
                    """;
            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.equals("0")) break;

            switch (option) {
                case "1" -> criarMedicamento(connection);
                case "2" -> lerMedicamentos(connection);
                case "3" -> atualizarMedicamento(connection);
                case "4" -> deletarMedicamento(connection);
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    public static void criarMedicamento(Connection connection) throws SQLException {
        String nome = JOptionPane.showInputDialog("Informe o nome do medicamento:");
        String fabricante = JOptionPane.showInputDialog("Informe o fabricante:");
        String preco = JOptionPane.showInputDialog("Informe o preço:");
        String estoque = JOptionPane.showInputDialog("Informe a quantidade em estoque:");

        String sql = "INSERT INTO Medicamentos (nome, fabricante, preco, estoque) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, fabricante);
            stmt.setDouble(3, Double.parseDouble(preco));
            stmt.setInt(4, Integer.parseInt(estoque));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Medicamento cadastrado com sucesso!");
        }
    }

    public static void lerMedicamentos(Connection connection) throws SQLException {
        StringBuilder result = new StringBuilder("Medicamentos:\n");
        String sql = "SELECT * FROM Medicamentos";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                        .append(", Nome: ").append(rs.getString("nome"))
                        .append(", Fabricante: ").append(rs.getString("fabricante"))
                        .append(", Preço: ").append(rs.getDouble("preco"))
                        .append(", Estoque: ").append(rs.getInt("estoque"))
                        .append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }

    public static void atualizarMedicamento(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do medicamento a ser atualizado:");
        String novoEstoque = JOptionPane.showInputDialog("Informe o novo estoque:");

        String sql = "UPDATE Medicamentos SET estoque = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(novoEstoque));
            stmt.setInt(2, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Medicamento atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Medicamento não encontrado!");
            }
        }
    }

    public static void deletarMedicamento(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do medicamento a ser deletado:");

        String sql = "DELETE FROM Medicamentos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Medicamento deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Medicamento não encontrado!");
            }
        }
    }

    //Menu de Clientes
    public static void menuClientes(Connection connection) throws SQLException {
        while (true) {
            String menu = """
                    Manipulação de Clientes:
                    1. Criar novo cliente
                    2. Ler clientes
                    3. Atualizar cliente
                    4. Deletar cliente
                    0. Voltar
                    """;
            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.equals("0")) break;

            switch (option) {
                case "1" -> criarCliente(connection);
                case "2" -> lerClientes(connection);
                case "3" -> atualizarCliente(connection);
                case "4" -> deletarCliente(connection);
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    public static void criarCliente(Connection connection) throws SQLException {
        String nome = JOptionPane.showInputDialog("Informe o nome do cliente:");
        String endereco = JOptionPane.showInputDialog("Informe o endereço:");
        String telefone = JOptionPane.showInputDialog("Informe o telefone:");

        String sql = "INSERT INTO Clientes (nome, endereco, telefone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, endereco);
            stmt.setString(3, telefone);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
        }
    }

    public static void lerClientes(Connection connection) throws SQLException {
        StringBuilder result = new StringBuilder("Clientes:\n");
        String sql = "SELECT * FROM Clientes";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                        .append(", Nome: ").append(rs.getString("nome"))
                        .append(", Endereço: ").append(rs.getString("endereco"))
                        .append(", Telefone: ").append(rs.getString("telefone"))
                        .append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }

    public static void atualizarCliente(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do cliente a ser atualizado:");
        String novoTelefone = JOptionPane.showInputDialog("Informe o novo telefone:");

        String sql = "UPDATE Clientes SET telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoTelefone);
            stmt.setInt(2, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
            }
        }
    }

    public static void deletarCliente(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do cliente a ser deletado:");

        String sql = "DELETE FROM Clientes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
            }
        }
    }

    //Menu de Vendas
    public static void menuVendas(Connection connection) throws SQLException {
        while (true) {
            String menu = """
                    Manipulação de Vendas:
                    1. Criar nova venda
                    2. Ler vendas
                    3. Atualizar venda
                    4. Deletar venda
                    0. Voltar
                    """;
            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.equals("0")) break;

            switch (option) {
                case "1" -> criarVenda(connection);
                case "2" -> lerVendas(connection);
                case "3" -> atualizarVenda(connection);
                case "4" -> deletarVenda(connection);
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    public static void criarVenda(Connection connection) throws SQLException {
        String clienteId = JOptionPane.showInputDialog("Informe o ID do cliente:");
        String data = JOptionPane.showInputDialog("Informe a data da venda (AAAA-MM-DD):");
        String total = JOptionPane.showInputDialog("Informe o valor total:");

        String sql = "INSERT INTO Vendas (data_venda, cliente_id, total) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(2, data);
            stmt.setInt(1, Integer.parseInt(clienteId));
            stmt.setDouble(3, Double.parseDouble(total));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Venda registrada com sucesso!");
        }
    }

    public static void lerVendas(Connection connection) throws SQLException {
        StringBuilder result = new StringBuilder("Vendas:\n");
        String sql = "SELECT * FROM Vendas";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                    .append(", Cliente ID: ").append(rs.getInt("cliente_id"))
                    .append(", Data: ").append(rs.getString("data_venda"))
                    .append(", Total: ").append(rs.getDouble("total"))
                    .append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }

    public static void atualizarVenda(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID da venda a ser atualizada:");
        String novoTotal = JOptionPane.showInputDialog("Informe o novo valor total:");

        String sql = "UPDATE Vendas SET total = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, Double.parseDouble(novoTotal));
            stmt.setInt(2, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Venda atualizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Venda não encontrada!");
            }
        }
    }

    public static void deletarVenda(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID da venda a ser deletada:");

        String sql = "DELETE FROM Vendas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Venda deletada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Venda não encontrada!");
            }
        }
    }


    //Menu de ItensVenda
    public static void menuItensVenda(Connection connection) throws SQLException {
        while (true) {
            String menu = """
                    Manipulação de ItensVenda:
                    1. Criar item de venda
                    2. Ler itens de venda
                    3. Atualizar item de venda
                    4. Deletar item de venda
                    0. Voltar
                    """;
            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.equals("0")) break;

            switch (option) {
                case "1" -> criarItemVenda(connection);
                case "2" -> lerItensVenda(connection);
                case "3" -> atualizarItemVenda(connection);
                case "4" -> deletarItemVenda(connection);
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    public static void criarItemVenda(Connection connection) throws SQLException {
        String vendaId = JOptionPane.showInputDialog("Informe o ID da venda:");
        String medicamentoId = JOptionPane.showInputDialog("Informe o ID do medicamento:");
        String quantidade = JOptionPane.showInputDialog("Informe a quantidade:");
        String precoUnitario = JOptionPane.showInputDialog("Informe o preço unitário:");

        String sql = "INSERT INTO ItensVenda (venda_id, medicamento_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(vendaId));
            stmt.setInt(2, Integer.parseInt(medicamentoId));
            stmt.setInt(3, Integer.parseInt(quantidade));
            stmt.setDouble(4, Double.parseDouble(precoUnitario));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Item de venda registrado com sucesso!");
        }
    }

    public static void lerItensVenda(Connection connection) throws SQLException {
        StringBuilder result = new StringBuilder("Itens de Venda:\n");
        String sql = "SELECT * FROM ItensVenda";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                    .append(", Venda ID: ").append(rs.getInt("venda_id"))
                    .append(", Medicamento ID: ").append(rs.getInt("medicamento_id"))
                    .append(", Quantidade: ").append(rs.getInt("quantidade"))
                    .append(", Preço Unitário: ").append(rs.getDouble("preco_unitario"))
                    .append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }

    public static void atualizarItemVenda(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do item de venda a ser atualizado:");
        String novaQuantidade = JOptionPane.showInputDialog("Informe a nova quantidade:");
        String novoPrecoUnitario = JOptionPane.showInputDialog("Informe o novo preço unitário:");

        String sql = "UPDATE ItensVenda SET quantidade = ?, preco_unitario = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(novaQuantidade));
            stmt.setDouble(2, Double.parseDouble(novoPrecoUnitario));
            stmt.setInt(3, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Item de venda atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Item de venda não encontrado!");
            }
        }
    }

    public static void deletarItemVenda(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do item de venda a ser deletado:");

        String sql = "DELETE FROM ItensVenda WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Item de venda deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Item de venda não encontrado!");
            }
        }
    }
}