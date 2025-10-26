
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Questao {
    private String questao;
    private String[] opcoes;
    private int points;
    private int correct;

    public String getQuestao() { 
        return questao;
     }
    public void setQuestao(String questao) { 
        this.questao = questao;
     }

    public String[] getOpcoes() { 
        return opcoes; 
    }
    public void setOpcoes(String[] opcoes) { 
        this.opcoes = opcoes; 
    }

    public int getPoints() { 
        return points; 
    }
    public void setPoints(int points) { 
        this.points = points; 
    }

    public int getCorrect() { 
        return correct; 
    }
    public void setCorrect(int correct) { 
        this.correct = correct;
     }

    public static Questao readFromFile(String caminhoArquivo) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
            Questao q = new Questao();

            content = content.replace("\n", "").replace("\r", "").replace(" ", "");

            int questaoStart = content.indexOf("\"questao\":\"") + 10;
            int questaoEnd = content.indexOf("\"", questaoStart);
            q.setQuestao(content.substring(questaoStart, questaoEnd));

            int opcoesStart = content.indexOf("[", content.indexOf("\"opcoes\"")) + 1;
            int opcoesEnd = content.indexOf("]", opcoesStart);
            String opcoesStr = content.substring(opcoesStart, opcoesEnd);
            String[] options = opcoesStr.replace("\"", "").split(",");
            q.setOpcoes(options);

            int pointsStart = content.indexOf("\"points\":") + 8;
            int pointsEnd = content.indexOf(",", pointsStart);
            if (pointsEnd == -1) pointsEnd = content.indexOf("}", pointsStart);
            q.setPoints(Integer.parseInt(content.substring(pointsStart, pointsEnd)));

            int correctStart = content.indexOf("\"correct\":") + 10;
            int correctEnd = content.indexOf("}", correctStart);
            q.setCorrect(Integer.parseInt(content.substring(correctStart, correctEnd)));

            return q;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
