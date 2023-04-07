
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Trabalho3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String titulos[] = new String[100];
        String autores[] = new String[100];
        String areasInteresse[] = new String[100];
        int numPaginas[] = new int[100];
        int quantidadeLivros = 0;

        int opcao = 0;
        while (opcao != 5) {
            System.out.println("Selecione uma opção:");
            System.out.println("1 - Adicionar livro");
            System.out.println("2 - Remover livro");
            System.out.println("3 - Buscar livro pelo título,nome do autor ou area de interesse");
            System.out.println("4 - Gerar relatório de livros");
            System.out.println("5 - Sair");
            if (sc.hasNextInt()) {
                opcao = sc.nextInt();
            } else {
                sc.nextLine();
                System.out.println("Opção inválida. Tente novamente.");
                continue;
            }
            switch (opcao) {
                case 1:
                    if (quantidadeLivros < 100) {
                        System.out.println("Digite o título do livro:");
                        sc.nextLine();
                        String titulo = sc.nextLine();
                        System.out.println("Digite o nome do autor:");
                        String autor = sc.nextLine();
                        System.out.println("Digite a área de interesse:");
                        String areaInteresse = sc.nextLine();
                        System.out.println("Digite o número de páginas:");
                        int numPaginasLivro = sc.nextInt();


                        titulos[quantidadeLivros] = titulo;
                        autores[quantidadeLivros] = autor;
                        areasInteresse[quantidadeLivros] = areaInteresse;
                        numPaginas[quantidadeLivros] = numPaginasLivro;

                        quantidadeLivros++;
                        System.out.println("Livro adicionado com sucesso!");


                        try {
                            FileWriter csvWriter = new FileWriter("livros.csv", true);
                            csvWriter.append(titulo + ",");
                            csvWriter.append(numPaginasLivro + ",");
                            csvWriter.append(autor + ",");
                            csvWriter.append(areaInteresse + "\n");
                            csvWriter.flush();
                            csvWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Não é possível adicionar mais livros. Limite máximo alcançado.");
                    }
                    break;
                case 2:
                    System.out.println("Digite o título do livro que deseja remover:");
                    sc.nextLine();
                    String tituloRemover = sc.nextLine();
                    boolean livroEncontrado = false;

                    for (int i = 0; i < quantidadeLivros; i++) {
                        if (titulos[i].equals(tituloRemover)) {
                            for (int j = i; j < quantidadeLivros - 1; j++) {
                                titulos[j] = titulos[j + 1];
                                autores[j] = autores[j + 1];
                                areasInteresse[j] = areasInteresse[j + 1];
                                numPaginas[j] = numPaginas[j + 1];
                            }
                            quantidadeLivros--;
                            livroEncontrado = true;
                            System.out.println("Livro removido com sucesso!");
                            break;
                        }
                    }
                    if (!livroEncontrado) {
                        System.out.println("Livro não encontrado.");
                    }


                    try {
                        String content = new String(Files.readAllBytes(Paths.get("livros.csv")));
                        content = content.replaceAll(tituloRemover + ",[0-9]*," + ".+?," + ".+?\n", "");
                        FileWriter csvWriter = new FileWriter("livros.csv");
                        csvWriter.write(content);
                        csvWriter.flush();
                        csvWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case 3:
                    System.out.println("Digite o título, nome do autor ou área de interesse do livro que deseja buscar:");
                    sc.nextLine();
                    String termoBusca = sc.nextLine();
                    livroEncontrado = false;

                    for (int i = 0; i < quantidadeLivros; i++) {
                        if (titulos[i].toLowerCase().contains(termoBusca.toLowerCase()) ||
                                autores[i].toLowerCase().contains(termoBusca.toLowerCase()) ||
                                areasInteresse[i].toLowerCase().contains(termoBusca.toLowerCase())) {

                            System.out.println("Livro encontrado:");
                            System.out.println("Título: " + titulos[i]);
                            System.out.println("Número de páginas: " + numPaginas[i]);
                            System.out.println("Autor: " + autores[i]);
                            System.out.println("Área de interesse: " + areasInteresse[i]);


                            livroEncontrado = true;
                        }
                    }

                    if (!livroEncontrado) {
                        System.out.println("Nenhum livro encontrado.");
                    }
                    break;
                case 4:
                    try {
                        Scanner scanner = new Scanner(new File("livros.csv"));
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            String[] parts = line.split(",");
                            System.out.println("Título: " + parts[0]);
                            System.out.println("Número de páginas: " + parts[1]);
                            System.out.println("Autor: " + parts[2]);
                            System.out.println("Área de interesse: " + parts[3]);
                            System.out.println(" ");
                        }
                        scanner.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}