public class PlotStyle {
    private String color;
    private String marker;
    private String width;

    PlotStyle(String color, boolean mark, String width) {
        this.color = color;
        this.marker = mark ? "*" : "none";
        this.width = width;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("    color=").append(color).append(",\n")
                .append("    mark=").append(marker).append(",\n");
        switch(width) {
            case "thin":
                builder.append("    ultra thin,\n")
                        .append("    mark size=1\n");
                break;
            case "thick":
                builder.append("    ultra thick,\n")
                        .append("    mark size=2\n");
                break;
        }
        return builder.toString();
    }
}
