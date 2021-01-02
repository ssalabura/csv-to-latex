import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LatexPlot {
    private String title;
    private String xlabel;
    private String ylabel;
    private Coordinates coordinates;

    LatexPlot(String title, String xlabel, String ylabel, Coordinates coordinates) {
        this.title = title;
        this.xlabel = xlabel;
        this.ylabel = ylabel;
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "\\documentclass{article}\n" +
                "\\usepackage{pgfplots}\n" +
                "\n" +
                "\\begin{document}\n" +
                "\n" +
                "\\begin{tikzpicture}\n" +
                "\\begin{axis}[\n" +
                "    title={" + title + "},\n" +
                "    xlabel={" + xlabel + "},\n" +
                "    ylabel={" + ylabel + "},\n" +
                "    ymajorgrids=true,\n" +
                "    grid style=dashed,\n" +
                "]\n" +
                "\n" +
                "\\addplot[\n" +
                "    color=blue,\n" +
                "    mark=square,\n" +
                "    ]\n" +
                "    coordinates {\n" +
                "    " + coordinates + "\n" +
                "    };\n" +
                "    \n" +
                "\\end{axis}\n" +
                "\\end{tikzpicture}\n" +
                "\n" +
                "\\end{document}";
    }

    File saveToFile(String fileName, boolean overwrite) throws IOException {
        int fileNo = 0;
        File newFile = new File(fileName + ".tex");
        while(!overwrite && newFile.exists()) {
            fileNo++;
            newFile = new File(fileName + "(" + fileNo + ")" + ".tex");
        }
        if (!overwrite && !newFile.createNewFile()) throw new IOException("Error creating new file");
        FileWriter writer = new FileWriter(newFile);
        writer.write(this.toString());
        writer.close();
        return newFile;
    }
}
