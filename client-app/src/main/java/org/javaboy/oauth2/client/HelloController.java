package org.javaboy.oauth2.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 *
 *
 * @param
 * @return 
 * @author 程龙[chenglonghy]
 * @date 2020/5/25 9:57
 * @history
 *         2020/5/25 9:57 程龙[chenglonghy]  create.
 */
@Controller
public class HelloController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/index.html")
    public String hello(String code, Model model) {
        if (code != null) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("code", code);
            map.add("client_id", "javaboy");
            map.add("client_secret", "123");
            map.add("redirect_uri", "http://172.23.15.208:8092/index.html");
            map.add("grant_type", "authorization_code");
            Map<String,String> resp = restTemplate.postForObject("http://172.23.15.208:8090/auth-server/oauth/token", map, Map.class);
            String access_token = resp.get("access_token");
            System.out.println(access_token);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + access_token);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> entity = restTemplate.exchange("http://172.23.15.208:8091/resource-server/admin/hello", HttpMethod.GET, httpEntity, String.class);
            model.addAttribute("msg", entity.getBody());
        }
        return "index";
    }
}
