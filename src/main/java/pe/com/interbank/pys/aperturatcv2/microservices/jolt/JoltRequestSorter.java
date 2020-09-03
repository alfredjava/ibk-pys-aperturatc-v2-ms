package pe.com.interbank.pys.aperturatcv2.microservices.jolt;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

@Component
public class JoltRequestSorter {

	@Autowired
	@Qualifier("sorterList")
	private Map<String, List<Object>> sorterList;

	protected String ordenarRequest(String request, String serviceId) {
		List<Object> specs = obtenerSpecs(serviceId);
		Chainr chainr = Chainr.fromSpec(specs);
		Object transformedOutput = chainr.transform(JsonUtils.jsonToObject(request));
		return JsonUtils.toJsonString(transformedOutput);
	}

	protected List<Object> obtenerSpecs(String serviceId) {
		return sorterList.get(serviceId);
	}

}
