package cloud.simple.service.util;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * @project : tykj-common
 * @createTime : 2017年5月17日 : 下午4:26:09
 * @author : lukewei
 * @description : fastJson消息转换器
 */
public class FastJsonMessageConverterFactory {

	private final static String DATEFORMAT = "YYYY-MM-dd HH:mm:ss";

	public static FastJsonHttpMessageConverter createConverter() {

		return createConverter(DATEFORMAT);
	} 
	
	public static FastJsonHttpMessageConverter createConverter(String dateFormat) {

		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = createFastJsonConfig(dateFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		return fastConverter;
	}

	public static FastJsonHttpMessageConverter createConverter(SerializerFeature[] features) {

		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = createFastJsonConfig(features);
		fastJsonConfig.setDateFormat(DATEFORMAT);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		return fastConverter;
	}

	public static FastJsonHttpMessageConverter createConverter(SerializerFeature[] features, String dateFormat) {

		FastJsonHttpMessageConverter fastConverter = createConverter(features);
		FastJsonConfig fastJsonConfig = createFastJsonConfig(features); 
		dateFormat = StringUtils.isEmpty(dateFormat) ? DATEFORMAT : dateFormat;
		fastJsonConfig.setDateFormat(dateFormat);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		return fastConverter;
	}

	private static FastJsonConfig createFastJsonConfig(String dateFormat) {

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		dateFormat = StringUtils.isEmpty(dateFormat) ? DATEFORMAT : dateFormat;
		fastJsonConfig.setDateFormat(dateFormat);
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		return fastJsonConfig;
	}

	private static FastJsonConfig createFastJsonConfig(SerializerFeature[] features) {

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		if (features == null || features.length == 0) {
			fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		} else {
			fastJsonConfig.setSerializerFeatures(features);
		}
		return fastJsonConfig;
	}
}
