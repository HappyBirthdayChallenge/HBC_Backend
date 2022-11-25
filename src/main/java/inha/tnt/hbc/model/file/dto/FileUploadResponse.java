package inha.tnt.hbc.model.file.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(SnakeCaseStrategy.class)
public class FileUploadResponse {

	@ApiModelProperty(value = "파일 ID", example = "1")
	private Long fileId;
	@ApiModelProperty(value = "클라이언트 ID", example = "1")
	private Long clientId;

	public static FileUploadResponse of(Long fileId, Long clientId) {
		return FileUploadResponse.builder()
			.fileId(fileId)
			.clientId(clientId)
			.build();
	}

}
