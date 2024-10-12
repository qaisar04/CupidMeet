package kz.baltabayev.storageservice.grpc;

import com.cupidmeet.qrservice.QrCodeServiceGrpc;
import com.cupidmeet.qrservice.QrCodeServiceOuterClass;
import io.grpc.stub.StreamObserver;
import kz.baltabayev.storageservice.domain.enumeration.S3Bucket;
import kz.baltabayev.storageservice.service.FileService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class QrCodeService extends QrCodeServiceGrpc.QrCodeServiceImplBase {

    private final FileService fileService;

    @Override
    public void uploadQrCode(QrCodeServiceOuterClass.QrCodeRequest request, StreamObserver<QrCodeServiceOuterClass.QrCodeResponse> responseObserver) {
        String path = fileService.saveFileToS3(request.getQrCodeImage().toByteArray(), S3Bucket.QR);

        QrCodeServiceOuterClass.QrCodeResponse response = QrCodeServiceOuterClass.QrCodeResponse.newBuilder()
                .setImagePath(path)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
