import java.io.*;
import java.util.ArrayList;

public class CSVData {
    private File file;
    private ArrayList<ArrayList<String>> data;

    CSVData(File file) throws IOException {
        this.file = file;
        data = parseFile(file);
    }

    ArrayList<ArrayList<String>> getData() {
        return new ArrayList<>(data.subList(1,data.size()));
    }

    ArrayList<String> getLabels() {
        return data.get(0);
    }

    private ArrayList<ArrayList<String>> parseFile(File file) throws IOException {
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while((line = reader.readLine()) != null) {
            ArrayList<String> parsedLine;
            try {
                parsedLine = parseLine(line);
            } catch(IOException e) {
                throw new IOException(e.getMessage() + " in line " + output.size());
            }
            if(output.size() > 1 && parsedLine.size() != output.get(output.size()-1).size()) {
                throw new IOException("Wrong number of fields in line " + output.size());
            }
            output.add(parsedLine);
        }
        if(output.size() == 0) throw new IOException("File is empty");
        return output;
    }

    private ArrayList<String> parseLine(String line) throws IOException {
        ArrayList<String> output = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        boolean inQuotes = false;

        for(int i=0; i<line.length(); i++) {
            char c = line.charAt(i);
            switch(c) {
                case ',':
                    if(!inQuotes) {
                        output.add(builder.toString());
                        builder.setLength(0);
                    } else {
                        builder.append(c);
                    }
                    break;
                case '"':
                    if(!inQuotes) inQuotes = true;
                    else if(line.length() > i+1 && line.charAt(i+1) == '"') {
                        builder.append(c);
                        i++;
                    } else {
                        inQuotes = false;
                    }
                    break;
                default:
                    builder.append(c);
            }
        }
        output.add(builder.toString());
        if(inQuotes) throw new IOException("Wrong quoting");
        return output;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(ArrayList<String> list : data) {
            builder.append(list);
            builder.append('\n');
        }
        return builder.toString();
    }
}
