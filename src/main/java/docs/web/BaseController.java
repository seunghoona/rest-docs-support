package docs.web;


import docs.model.JsonDocumentFieldType;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sample")
public class BaseController {

	@GetMapping
	public ResponseEntity<BaseResponse> getQueryParam(BaseRequest baseRequest) {
		BaseResponse baseResponse = getBaseResponse();
		return ResponseEntity.ok(baseResponse);
	}

	@GetMapping("/path/{path-name}")
	public ResponseEntity<BaseResponse> getPathParam(
		@PathVariable("path-name") String pathName,
		BaseRequest baseRequest) {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setData(new SampleOtherOrder());
		return ResponseEntity.ok(baseResponse);
	}

	@GetMapping("/no-data")
	public ResponseEntity<BaseResponse> getNoData(
		BaseRequest baseRequest) {
		BaseResponse baseResponse = new BaseResponse();

		return ResponseEntity.ok(baseResponse);
	}
	@PostMapping("/request-body")
	public ResponseEntity<BaseResponse> saveRequestBody(
		@RequestBody String name) {

		BaseResponse baseResponse = new BaseResponse();

		return ResponseEntity.ok(baseResponse);
	}	@GetMapping("/enum-type")

	private BaseResponse<?> getBaseResponse() {
		BaseResponse baseResponse = new BaseResponse();
		SampleProduct sampleProduct = new SampleProduct();
		sampleProduct.setSamples(List.of(new SampleOrder()));
		baseResponse.setData(sampleProduct);
		return baseResponse;
	}

	static class SampleOrder {

		private String orderName = "";

		public String getOrderName() {
			return orderName;
		}

		public void setOrderName(String orderName) {
			this.orderName = orderName;
		}
	}

	static class SampleOtherOrder {

		private String order = "";


		public String getOrder() {
			return order;
		}

	}

	static class SampleProduct {

		private String price = "";
		private List<SampleOrder> samples = new ArrayList<>();

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public List<SampleOrder> getSamples() {
			return samples;
		}

		public void setSamples(List<SampleOrder> samples) {
			this.samples = samples;
		}
	}

	static class Header {

		private String code = "000";
		private String message = "정상처리";

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	static class BaseResponse<T> {

		private Header headers = new Header();
		private T data;

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public Header getHeaders() {
			return headers;
		}

		public void setHeaders(Header headers) {
			this.headers = headers;
		}
	}

	static class BaseRequest {

		private String name;
		private String sortType;

		public String getName() {
			return name;
		}

		public void setData(String name) {
			this.name = name;
		}

		public String getSortType() {
			return sortType;
		}

		public void setSortType(String sortType) {
			this.sortType = sortType;
		}
	}
}
