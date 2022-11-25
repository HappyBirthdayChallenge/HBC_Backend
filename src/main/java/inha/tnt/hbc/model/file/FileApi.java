package inha.tnt.hbc.model.file;

import static org.springframework.http.MediaType.*;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import inha.tnt.hbc.annotation.Audio;
import inha.tnt.hbc.annotation.Image;
import inha.tnt.hbc.annotation.Video;
import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.Void;
import inha.tnt.hbc.model.file.dto.FileUploadResponse;

@Api(tags = "파일 API")
@Validated
@RequestMapping("/files")
public interface FileApi {

	@ApiOperation(value = "이미지 파일 업로드", notes = ""
		+ "1. PNG, JPG, JPEG, GIF 형식만 지원합니다.\n"
		+ "2. 최대 10MB까지 업로드할 수 있습니다."
	)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "message_id", value = "메시지 PK", example = "1", required = true),
		@ApiImplicitParam(name = "client_id", value = "클라이언트 ID", example = "1", required = true)
	})
	@ApiResponses({
		@ApiResponse(code = 1, response = FileUploadResponse.class, message = ""
			+ "status: 200 | code: R-FI001 | message: 이미지 파일 업로드에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-RM003 | message: 존재하지 않는 메시지입니다.\n"
			+ "status: 400 | code: E-F002 | message: 삭제된 메시지에 파일을 첨부할 수 없습니다.\n"
			+ "status: 400 | code: E-F003 | message: 업로드된 메시지에 파일을 첨부할 수 없습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping(value = "/upload/image", consumes = MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<ResultResponse> uploadImage(@Image @RequestParam(name = "image") MultipartFile image,
		@RequestParam(name = "message_id") Long messageId, @RequestParam(name = "client_id") Long clientId);

	@ApiOperation(value = "동영상 파일 업로드", notes = ""
		+ "1. MP4 형식만 지원합니다.\n"
		+ "2. 최대 300MB까지 업로드할 수 있습니다."
	)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "message_id", value = "메시지 PK", example = "1", required = true),
		@ApiImplicitParam(name = "client_id", value = "클라이언트 ID", example = "1", required = true)
	})
	@ApiResponses({
		@ApiResponse(code = 1, response = FileUploadResponse.class, message = ""
			+ "status: 200 | code: R-FI003 | message: 동영상 파일 업로드에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-RM003 | message: 존재하지 않는 메시지입니다.\n"
			+ "status: 400 | code: E-F002 | message: 삭제된 메시지에 파일을 첨부할 수 없습니다.\n"
			+ "status: 400 | code: E-F003 | message: 업로드된 메시지에 파일을 첨부할 수 없습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping(value = "/upload/video", consumes = MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<ResultResponse> uploadVideo(@Video @RequestParam(name = "video") MultipartFile video,
		@RequestParam(name = "message_id") Long messageId, @RequestParam(name = "client_id") Long clientId);

	@ApiOperation(value = "오디오 파일 업로드", notes = ""
		+ "1. M4A 형식만 지원합니다.\n"
		+ "2. 최대 10MB까지 업로드할 수 있습니다."
	)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "message_id", value = "메시지 PK", example = "1", required = true),
		@ApiImplicitParam(name = "client_id", value = "클라이언트 ID", example = "1", required = true)

	})
	@ApiResponses({
		@ApiResponse(code = 1, response = FileUploadResponse.class, message = ""
			+ "status: 200 | code: R-FI002 | message: 오디오 파일 업로드에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-RM003 | message: 존재하지 않는 메시지입니다.\n"
			+ "status: 400 | code: E-F002 | message: 삭제된 메시지에 파일을 첨부할 수 없습니다.\n"
			+ "status: 400 | code: E-F003 | message: 업로드된 메시지에 파일을 첨부할 수 없습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping(value = "/upload/audio", consumes = MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<ResultResponse> uploadAudio(@Audio @RequestParam(name = "audio") MultipartFile audio,
		@RequestParam(name = "message_id") Long messageId, @RequestParam(name = "client_id") Long clientId);

	@ApiOperation(value = "메시지 파일 삭제")
	@ApiImplicitParam(name = "file_id", value = "메시지 파일 PK", example = "1", required = true)
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-FI004 | message: 메시지 파일 삭제에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-F001 | message: 존재하지 않는 파일입니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@DeleteMapping("/{file_id}")
	ResponseEntity<ResultResponse> deleteMessageFile(@PathVariable(name = "file_id") Long fileId);

}
