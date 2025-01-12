import java.math.BigDecimal;
import java.sql.*;
import javax.swing.JOptionPane;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Farmacia";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            while (true) {
                String menu = "Escolha uma tabela para manipular:\n1. Medicamentos\n2. Clientes\n3. Vendas\n4. ItensVenda\n5. Receitas Médicas\n6. Medicamentos Receitados\n7. Fornecedores\n8. Produtos Fornecidos\n9. Ordens de Compra\n10. Funcionários\n0. Sair";
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
                    case "5" -> menuReceitasMedicas(connection);
                    case "6" -> menuMedicamentosReceitados(connection);
                    case "7" -> menuFornecedores(connection);
                    case "8" -> menuProdutosFornecidos(connection);
                    case "9" -> menuOrdensDeCompra(connection);
                    case "10" -> menuFuncionarios(connection);
                    default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de conexão: " + e.getMessage());
        }
    }
    

    public static void menuMedicamentos(Connection connection) throws SQLException {
        while (true) {
            String menu = """
                    Manipulação de Medicamentos:
                    1. Criar novo medicamento
                    2. Ler medicamentos
                    3. Atualizar medicamento
                    4. Deletar medicamento,0
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
        String categoria = JOptionPane.showInputDialog("Informe o Categoria:");
        String preco = JOptionPane.showInputDialog("Informe o preço:");
        String estoque = JOptionPane.showInputDialog("Informe a quantidade em estoque:");

        String sql = "INSERT INTO Medicamentos (nome, categoria, preco, estoque) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, categoria);
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
                        .append(", Categoria: ").append(rs.getString("categoria"))
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

        String sql = "INSERT INTO Vendas (data_hora, cliente_id, total) VALUES (?, ?, ?)";
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
                    .append(", Data: ").append(rs.getString("data_hora"))
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
    public static void menuReceitasMedicas(Connection connection) throws SQLException {
        while (true) {
            String menu = """
                    Manipulação de Receitas Médicas:
                    1. Criar nova receita médica
                    2. Ler receitas médicas
                    3. Atualizar receita médica
                    4. Deletar receita médica
                    0. Voltar
                    """;
            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.equals("0")) break;
    
            switch (option) {
                case "1" -> criarReceitaMedica(connection);
                case "2" -> lerReceitasMedicas(connection);
                case "3" -> atualizarReceitaMedica(connection);
                case "4" -> deletarReceitaMedica(connection);
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }
    public static void criarReceitaMedica(Connection connection) throws SQLException {
        String clienteId = JOptionPane.showInputDialog("Informe o ID do cliente:");
        String medicoNome = JOptionPane.showInputDialog("Informe o nome do médico:");
        String dataEmissao = JOptionPane.showInputDialog("Informe a data de emissão da receita (YYYY-MM-DD):");
    
        String sql = "INSERT INTO ReceitasMedicas (cliente_id, medico_nome, data_emissao) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(clienteId));
            stmt.setString(2, medicoNome);
            stmt.setDate(3, java.sql.Date.valueOf(dataEmissao));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Receita médica cadastrada com sucesso!");
        }
    }
    public static void lerReceitasMedicas(Connection connection) throws SQLException {
        StringBuilder result = new StringBuilder("Receitas Médicas:\n");
        String sql = "SELECT * FROM ReceitasMedicas";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                      .append(", Cliente ID: ").append(rs.getInt("cliente_id"))
                      .append(", Médico: ").append(rs.getString("medico_nome"))
                      .append(", Data de Emissão: ").append(rs.getDate("data_emissao"))
                      .append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }
    public static void atualizarReceitaMedica(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID da receita médica a ser atualizada:");
        String novoMedicoNome = JOptionPane.showInputDialog("Informe o novo nome do médico:");
        String novaDataEmissao = JOptionPane.showInputDialog("Informe a nova data de emissão (YYYY-MM-DD):");
    
        String sql = "UPDATE ReceitasMedicas SET medico_nome = ?, data_emissao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoMedicoNome);
            stmt.setDate(2, java.sql.Date.valueOf(novaDataEmissao));
            stmt.setInt(3, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Receita médica atualizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Receita médica não encontrada!");
            }
        }
    }
    public static void deletarReceitaMedica(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID da receita médica a ser deletada:");
    
        String sql = "DELETE FROM ReceitasMedicas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Receita médica deletada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Receita médica não encontrada!");
            }
        }
    }
    public static void menuMedicamentosReceitados(Connection connection) throws SQLException {
        while (true) {
            String menu = """
                    Manipulação de Medicamentos Receitados:
                    1. Criar novo medicamento receitado
                    2. Ler medicamentos receitados
                    3. Atualizar medicamento receitado
                    4. Deletar medicamento receitado
                    0. Voltar
                    """;
            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.equals("0")) break;
    
            switch (option) {
                case "1" -> criarMedicamentoReceitado(connection);
                case "2" -> lerMedicamentosReceitados(connection);
                case "3" -> atualizarMedicamentoReceitado(connection);
                case "4" -> deletarMedicamentoReceitado(connection);
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }
    public static void criarMedicamentoReceitado(Connection connection) throws SQLException {
        String receitaId = JOptionPane.showInputDialog("Informe o ID da receita médica:");
        String medicamentoId = JOptionPane.showInputDialog("Informe o ID do medicamento:");
        String quantidade = JOptionPane.showInputDialog("Informe a quantidade:");
    
        String sql = "INSERT INTO MedicamentosReceitados (receita_id, medicamento_id, quantidade) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(receitaId));
            stmt.setInt(2, Integer.parseInt(medicamentoId));
            stmt.setInt(3, Integer.parseInt(quantidade));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Medicamento receitado cadastrado com sucesso!");
        }
    }
    public static void lerMedicamentosReceitados(Connection connection) throws SQLException {
        StringBuilder result = new StringBuilder("Medicamentos Receitados:\n");
        String sql = "SELECT * FROM MedicamentosReceitados";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                      .append(", Receita ID: ").append(rs.getInt("receita_id"))
                      .append(", Medicamento ID: ").append(rs.getInt("medicamento_id"))
                      .append(", Quantidade: ").append(rs.getInt("quantidade"))
                      .append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }
    public static void atualizarMedicamentoReceitado(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do medicamento receitado a ser atualizado:");
        String novaQuantidade = JOptionPane.showInputDialog("Informe a nova quantidade:");
    
        String sql = "UPDATE MedicamentosReceitados SET quantidade = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(novaQuantidade));
            stmt.setInt(2, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Medicamento receitado atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Medicamento receitado não encontrado!");
            }
        }
    }
    public static void deletarMedicamentoReceitado(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do medicamento receitado a ser deletado:");
    
        String sql = "DELETE FROM MedicamentosReceitados WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Medicamento receitado deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Medicamento receitado não encontrado!");
            }
        }
    }
    public static void menuFornecedores(Connection connection) throws SQLException {
        while (true) {
            String menu = """
                    Manipulação de Fornecedores:
                    1. Criar novo fornecedor
                    2. Ler fornecedores
                    3. Atualizar fornecedor
                    4. Deletar fornecedor
                    0. Voltar
                    """;
            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.equals("0")) break;
    
            switch (option) {
                case "1" -> criarFornecedor(connection);
                case "2" -> lerFornecedores(connection);
                case "3" -> atualizarFornecedor(connection);
                case "4" -> deletarFornecedor(connection);
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }
    public static void criarFornecedor(Connection connection) throws SQLException {
        String nome = JOptionPane.showInputDialog("Informe o nome do fornecedor:");
        String contato = JOptionPane.showInputDialog("Informe o contato do fornecedor:");
        String endereco = JOptionPane.showInputDialog("Informe o endereço do fornecedor:");
    
        String sql = "INSERT INTO Fornecedores (nome, contato, endereco) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, contato);
            stmt.setString(3, endereco);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Fornecedor cadastrado com sucesso!");
        }
    }
    public static void lerFornecedores(Connection connection) throws SQLException {
        StringBuilder result = new StringBuilder("Fornecedores:\n");
        String sql = "SELECT * FROM Fornecedores";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                      .append(", Nome: ").append(rs.getString("nome"))
                      .append(", Contato: ").append(rs.getString("contato"))
                      .append(", Endereço: ").append(rs.getString("endereco"))
                      .append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }
    public static void atualizarFornecedor(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do fornecedor a ser atualizado:");
        String novoNome = JOptionPane.showInputDialog("Informe o novo nome:");
        String novoContato = JOptionPane.showInputDialog("Informe o novo contato:");
        String novoEndereco = JOptionPane.showInputDialog("Informe o novo endereço:");
    
        String sql = "UPDATE Fornecedores SET nome = ?, contato = ?, endereco = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novoContato);
            stmt.setString(3, novoEndereco);
            stmt.setInt(4, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Fornecedor atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Fornecedor não encontrado!");
            }
        }
    }
    public static void deletarFornecedor(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do fornecedor a ser deletado:");
    
        String sql = "DELETE FROM Fornecedores WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Fornecedor deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Fornecedor não encontrado!");
            }
        }
    }
    public static void menuProdutosFornecidos(Connection connection) throws SQLException {
        while (true) {
            String menu = """
                    Manipulação de Produtos Fornecidos:
                    1. Criar novo produto fornecido
                    2. Ler produtos fornecidos
                    3. Atualizar produto fornecido
                    4. Deletar produto fornecido
                    0. Voltar
                    """;
            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.equals("0")) break;
    
            switch (option) {
                case "1" -> criarProdutoFornecido(connection);
                case "2" -> lerProdutosFornecidos(connection);
                case "3" -> atualizarProdutoFornecido(connection);
                case "4" -> deletarProdutoFornecido(connection);
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }
    public static void criarProdutoFornecido(Connection connection) throws SQLException {
    String fornecedorId = JOptionPane.showInputDialog("Informe o ID do fornecedor:");
    String medicamentoId = JOptionPane.showInputDialog("Informe o ID do medicamento:");
    String preco = JOptionPane.showInputDialog("Informe o preço do produto:");
    String dataEntrega = JOptionPane.showInputDialog("Informe a data de entrega (YYYY-MM-DD):");

    String sql = "INSERT INTO ProdutosFornecidos (fornecedor_id, medicamento_id, preco, data_entrega) VALUES (?, ?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, Integer.parseInt(fornecedorId));
        stmt.setInt(2, Integer.parseInt(medicamentoId));
        stmt.setBigDecimal(3, new BigDecimal(preco));
        stmt.setDate(4, Date.valueOf(dataEntrega));
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Produto fornecido cadastrado com sucesso!");
        }
    }
    public static void lerProdutosFornecidos(Connection connection) throws SQLException {
        StringBuilder result = new StringBuilder("Produtos Fornecidos:\n");
        String sql = "SELECT * FROM ProdutosFornecidos";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                      .append(", Fornecedor ID: ").append(rs.getInt("fornecedor_id"))
                      .append(", Medicamento ID: ").append(rs.getInt("medicamento_id"))
                      .append(", Preço: ").append(rs.getBigDecimal("preco"))
                      .append(", Data de Entrega: ").append(rs.getDate("data_entrega"))
                      .append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }
    public static void atualizarProdutoFornecido(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do produto fornecido a ser atualizado:");
        String novoPreco = JOptionPane.showInputDialog("Informe o novo preço:");
        String novaDataEntrega = JOptionPane.showInputDialog("Informe a nova data de entrega (YYYY-MM-DD):");
    
        String sql = "UPDATE ProdutosFornecidos SET preco = ?, data_entrega = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBigDecimal(1, new BigDecimal(novoPreco));
            stmt.setDate(2, Date.valueOf(novaDataEntrega));
            stmt.setInt(3, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Produto fornecido atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto fornecido não encontrado!");
            }
        }
    }
    public static void deletarProdutoFornecido(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do produto fornecido a ser deletado:");
    
        String sql = "DELETE FROM ProdutosFornecidos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Produto fornecido deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto fornecido não encontrado!");
            }
        }
    }
    public static void menuOrdensDeCompra(Connection connection) throws SQLException {
        while (true) {
            String menu = """
                    Manipulação de Ordens de Compra:
                    1. Criar nova ordem de compra
                    2. Ler ordens de compra
                    3. Atualizar ordem de compra
                    4. Deletar ordem de compra
                    0. Voltar
                    """;
            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.equals("0")) break;
    
            switch (option) {
                case "1" -> criarOrdemDeCompra(connection);
                case "2" -> lerOrdensDeCompra(connection);
                case "3" -> atualizarOrdemDeCompra(connection);
                case "4" -> deletarOrdemDeCompra(connection);
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }
    public static void criarOrdemDeCompra(Connection connection) throws SQLException {
        String fornecedorId = JOptionPane.showInputDialog("Informe o ID do fornecedor:");
        String medicamentoId = JOptionPane.showInputDialog("Informe o ID do medicamento:");
        String quantidade = JOptionPane.showInputDialog("Informe a quantidade:");
        String dataPrevistaEntrega = JOptionPane.showInputDialog("Informe a data prevista de entrega (YYYY-MM-DD):");
    
        String sql = "INSERT INTO OrdensDeCompra (fornecedor_id, medicamento_id, quantidade, data_prevista_entrega) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(fornecedorId));
            stmt.setInt(2, Integer.parseInt(medicamentoId));
            stmt.setInt(3, Integer.parseInt(quantidade));
            stmt.setDate(4, Date.valueOf(dataPrevistaEntrega));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Ordem de compra cadastrada com sucesso!");
        }
    }
    public static void lerOrdensDeCompra(Connection connection) throws SQLException {
        StringBuilder result = new StringBuilder("Ordens de Compra:\n");
        String sql = "SELECT * FROM OrdensDeCompra";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                      .append(", Fornecedor ID: ").append(rs.getInt("fornecedor_id"))
                      .append(", Medicamento ID: ").append(rs.getInt("medicamento_id"))
                      .append(", Quantidade: ").append(rs.getInt("quantidade"))
                      .append(", Data Prevista de Entrega: ").append(rs.getDate("data_prevista_entrega"))
                      .append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }
    public static void atualizarOrdemDeCompra(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID da ordem de compra a ser atualizada:");
        String novaQuantidade = JOptionPane.showInputDialog("Informe a nova quantidade:");
        String novaDataPrevistaEntrega = JOptionPane.showInputDialog("Informe a nova data prevista de entrega (YYYY-MM-DD):");
    
        String sql = "UPDATE OrdensDeCompra SET quantidade = ?, data_prevista_entrega = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(novaQuantidade));
            stmt.setDate(2, Date.valueOf(novaDataPrevistaEntrega));
            stmt.setInt(3, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Ordem de compra atualizada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Ordem de compra não encontrada!");
            }
        }
    }
    public static void deletarOrdemDeCompra(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID da ordem de compra a ser deletada:");
    
        String sql = "DELETE FROM OrdensDeCompra WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Ordem de compra deletada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Ordem de compra não encontrada!");
            }
        }
    }
    public static void menuFuncionarios(Connection connection) throws SQLException {
        while (true) {
            String menu = """
                    Manipulação de Funcionários:
                    1. Criar novo funcionário
                    2. Ler funcionários
                    3. Atualizar funcionário
                    4. Deletar funcionário
                    0. Voltar
                    """;
            String option = JOptionPane.showInputDialog(menu);
            if (option == null || option.equals("0")) break;    
    
            switch (option) {
                case "1" -> criarFuncionario(connection);
                case "2" -> lerFuncionarios(connection);
                case "3" -> atualizarFuncionario(connection);
                case "4" -> deletarFuncionario(connection);
                default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }
    public static void criarFuncionario(Connection connection) throws SQLException {
        String nome = JOptionPane.showInputDialog("Informe o nome do funcionário:");
        String dni = JOptionPane.showInputDialog("Informe o DNI do funcionário:");
        String cargo = JOptionPane.showInputDialog("Informe o cargo (Farmaceutico, Caixa, Repositor):");
        String salario = JOptionPane.showInputDialog("Informe o salário:");
    
        String sql = "INSERT INTO Funcionarios (nome, dni, cargo, salario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, dni);
            stmt.setString(3, cargo);
            stmt.setDouble(4, Double.parseDouble(salario));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso!");
        }
    }
    public static void lerFuncionarios(Connection connection) throws SQLException {
        StringBuilder result = new StringBuilder("Funcionários:\n");
        String sql = "SELECT * FROM Funcionarios";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id"))
                      .append(", Nome: ").append(rs.getString("nome"))
                      .append(", DNI: ").append(rs.getString("dni"))
                      .append(", Cargo: ").append(rs.getString("cargo"))
                      .append(", Salário: ").append(rs.getDouble("salario"))
                      .append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }
    public static void atualizarFuncionario(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do funcionário a ser atualizado:");
        String novoNome = JOptionPane.showInputDialog("Informe o novo nome do funcionário:");
        String novoCargo = JOptionPane.showInputDialog("Informe o novo cargo (Farmaceutico, Caixa, Repositor):");
        String novoSalario = JOptionPane.showInputDialog("Informe o novo salário:");
    
        String sql = "UPDATE Funcionarios SET nome = ?, cargo = ?, salario = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novoCargo);
            stmt.setDouble(3, Double.parseDouble(novoSalario));
            stmt.setInt(4, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Funcionário não encontrado!");
            }
        }
    }
    public static void deletarFuncionario(Connection connection) throws SQLException {
        String id = JOptionPane.showInputDialog("Informe o ID do funcionário a ser deletado:");
    
        String sql = "DELETE FROM Funcionarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Funcionário deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Funcionário não encontrado!");
            }
        }
    }
                                                                                     
}