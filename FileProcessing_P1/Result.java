// data structure to pass the analyzed data to thread
public class Result {
    private final String fileName;
    private final int lineCount;
    private final int uniqueWordCount;
    private final String longestLine;

    public Result(String fileName,int lineCount,int uniqueWordCount,String longestLine){
        this.fileName = fileName;
        this.lineCount = lineCount;
        this.uniqueWordCount = uniqueWordCount;
        this.longestLine = longestLine;
    }
    // a special "poison pill" market to signal the writer thread to stop
    public static Result POISON_PILL = new Result(null,0,0,null);

    @Override
    public String toString(){
        if(this == POISON_PILL){
            return "--- END OF PROCESSING --";
        }
        return String.format(
                "File %s | Lines %d | Unique Words %d | Longest Line Length: %d",
                fileName,lineCount,uniqueWordCount,(longestLine != null ? longestLine.length() : 0)
        );
    }
}
