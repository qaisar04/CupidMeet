package com.cupidmeet.qrservice.service.impl;

import com.cupidmeet.runtimecore.exception.CustomRuntimeException;
import com.cupidmeet.qrservice.QrCodeServiceGrpc.QrCodeServiceBlockingStub;
import com.cupidmeet.qrservice.QrCodeServiceOuterClass;
import com.cupidmeet.qrservice.message.Messages;
import com.cupidmeet.qrservice.service.QrService;
import com.google.protobuf.ByteString;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class QrServiceImpl implements QrService {

    @Value("${qr.charset}")
    private String charset;

    @Value("${qr.height}")
    private Integer height;

    @Value("${qr.width}")
    private Integer width;

    @GrpcClient("qrCodeService")
    private QrCodeServiceBlockingStub blockingStub;

    @Override
    public CompletableFuture<Void> generateQRAsync(HttpServletResponse response, String link) {
        return CompletableFuture.runAsync(() -> {
            try {
                byte[] imageBytes = generateQrCodeBytes(link);
                long timestamp = System.currentTimeMillis();

                response.setContentType("image/png");
                response.setHeader("Content-Disposition", "inline; filename=qr-code-" + timestamp + ".png");

                var outputStream = response.getOutputStream();
                outputStream.write(imageBytes);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                throw new CustomRuntimeException(Messages.QR_GENERATION_ERROR, link, e);
            }
        });
    }

    @Override
    public String generateQRPath(String link) {
        try {
            byte[] imageBytes = generateQrCodeBytes(link);

            QrCodeServiceOuterClass.QrCodeRequest request = QrCodeServiceOuterClass.QrCodeRequest.newBuilder()
                    .setQrCodeImage(ByteString.copyFrom(imageBytes))
                    .build();

            QrCodeServiceOuterClass.QrCodeResponse response = blockingStub.uploadQrCode(request);

            return response.getImagePath();
        } catch (Exception e) {
            throw new CustomRuntimeException(Messages.QR_GENERATION_ERROR, link, e);
        }
    }

    private byte[] generateQrCodeBytes(String link) throws Exception {
        BitMatrix matrix = new MultiFormatWriter()
                .encode(
                        new String(link.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, width, height
                );

        var bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
        var byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }
}
