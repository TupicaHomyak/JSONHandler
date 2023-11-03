package org.example;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        //код, который будет читать JSON-файл в строку
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonFile = Files.readString(Paths.get("C:\\Users\\Хомякмен\\Desktop\\mapp.json"));

        //код, который будет парсить JSON-данные в объект класса JsonNode
        JsonNode jsonData = objectMapper.readTree(jsonFile);
        //данные о станциях на каждой линии, а также массив самих линий
        JsonNode stations = jsonData.get("stations");
        JsonNode lines = jsonData.get("lines");

        //массив с линиями  и на основе каждой линии объект, который можно будет изменять:
        for (JsonNode line : lines) {
            ObjectNode lineNode = (ObjectNode) line;
            //удаляем ключ color
            lineNode.remove("color");
            String lineNumber = line.get("number").asText();
            JsonNode statiionsList = stations.get(lineNumber);
            //добавление ключа stationsCount
            int stationsCount = statiionsList.size();
            lineNode.put("stationsCount", stationsCount);
        }
        //изменённый объект “lines”, отдельный JSON-файл
        ObjectMapper mapper = new ObjectMapper();
        File output = new File("export.json");
        mapper.writeValue(output, lines);
    }
}