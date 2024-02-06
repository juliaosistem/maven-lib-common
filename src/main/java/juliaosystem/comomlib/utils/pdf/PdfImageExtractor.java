package juliaosystem.comomlib.utils.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfImageExtractor {

    public static List<File> extractImagesFromPDF(File pdfFile, String outputDirectoryPath, int targetWidth, int targetHeight,String imageName) throws IOException {
        PDDocument document = PDDocument.load(pdfFile);
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        List<File> imageFiles = new ArrayList<>();

        for (int pageNumber = 1; pageNumber <= document.getNumberOfPages(); pageNumber++) {
            BufferedImage originalImage = pdfRenderer.renderImageWithDPI(pageNumber - 1, 300);

            // Crear una nueva imagen con el tamaño deseado
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());

            // Escalar la imagen original a las dimensiones deseadas
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            graphics.dispose();

            // Guardar la imagen redimensionada en el disco
            String outputFileName = imageName + pageNumber + ".png";
            File outputFile = new File(outputDirectoryPath, outputFileName);
            ImageIO.write(resizedImage, "png", outputFile);
            imageFiles.add(outputFile);
        }

        document.close();

        return imageFiles;
    }

}
