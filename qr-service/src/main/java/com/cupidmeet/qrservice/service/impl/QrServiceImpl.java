package com.cupidmeet.qrservice.service.impl;

import com.cupidmeet.commonmessage.exception.CommonRuntimeException;
import com.cupidmeet.qrservice.message.Messages;
import com.cupidmeet.qrservice.service.QrService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.CompletableFuture;

@Service
public class QrServiceImpl implements QrService {

    @Value("${qr.charset}")
    private String charset;

    @Value("${qr.height}")
    private Integer height;

    @Value("${qr.width}")
    private Integer width;

    @Override
    public CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String link) {
        return CompletableFuture.runAsync(() -> {
            BitMatrix matrix;
            try {
                matrix = new MultiFormatWriter()
                        .encode(
                                new String(link.getBytes(charset), charset),
                                BarcodeFormat.QR_CODE, width, height
                        );

                var bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
                response.setContentType("image/png");
                response.setHeader("Content-Disposition", "inline; filename=qr-code.png");

                var outputStream = response.getOutputStream();

                var byteArrayOutputStream = new ByteArrayOutputStream();

                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                byteArrayOutputStream.writeTo(outputStream);

                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                throw new CommonRuntimeException(Messages.QR_GENERATION_ERROR, link, e);
            }
        });
    }


}
