package org.example.Service;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {

    private static final String FILE_PATH = "path/to/your/huge-log.txt";

    public LogChunk getLogChunk(long startByte, int lineLimit) throws IOException {
        List<String> lines = new ArrayList<>();
        long nextBytePointer;

        try (RandomAccessFile file = new RandomAccessFile(FILE_PATH, "r")) {
            // Jump directly to the requested part of the 1GB file
            file.seek(startByte);

            String line;
            while (lines.size() < lineLimit && (line = file.readLine()) != null) {
                lines.add(line);
            }
            // Record where we stopped so the frontend knows where to start next
            nextBytePointer = file.getFilePointer();
        }

        return new LogChunk(lines, nextBytePointer);
    }
}

// Simple DTO to send back to the browser
public record LogChunk(List<String> lines, long nextBytePointer) {}