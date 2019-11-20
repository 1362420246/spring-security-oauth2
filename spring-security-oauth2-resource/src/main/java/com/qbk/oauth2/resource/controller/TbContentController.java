package com.qbk.oauth2.resource.controller;


import com.qbk.oauth2.resource.domain.TbContent;
import com.qbk.oauth2.resource.dto.ResponseResult;
import com.qbk.oauth2.resource.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 验证方式：
 * 访问源服务器：http://localhost:8089/contents/  无权限
 *
 *  8088 为认证服务器
 *  8089 为资源服务器
 *
 * 1. 访问获取授权码：http://localhost:8088/oauth/authorize?client_id=client&response_type=code
 * 2. 选择授权后会跳转到回调地址，浏览器地址上还会包含一个授权码（code=A4vIys）如：https://www.quboka.cn/?code=A4vIys
 * 3. 通过授权码向服务器申请令牌，通过 CURL 或是 Postman 请求：
 *    url:http://client:secret@localhost:8088/oauth/token
 *    POST -H "Content-Type: application/x-www-form-urlencoded" -d 'grant_type=authorization_code&code=A4vIys'
 * 返回json：
 * {
 *     "access_token": "ea5069cc-49be-4f19-b920-87c00d6e2bea",
 *     "token_type": "bearer",
 *     "expires_in": 43199,
 *     "scope": "app"
 * }
 * 4. 携带令牌访问资源服务器
 * 此处以获取全部资源为例，其它请求方式一样，可以参考我源码中的单元测试代码。可以使用以下方式请求：
 *
 * （1） 直接请求带参数方式：http://localhost:8089/contents?access_token=yourAccessToken
 * （2）使用 Headers 方式：需要在请求头增加 Authorization: Bearer yourAccessToken
 * curl --location --request GET "http://localhost:8089/contents" --header "Content-Type: application/json" --header "Authorization: Bearer yourAccessToken"
 * 5. 访问源服务器：http://localhost:8089/contents/
 */
@RestController
public class TbContentController {

    @Autowired
    private TbContentService tbContentService;

    /**
     * 获取全部资源
     *
     * @return
     */
    @GetMapping("/")
    public ResponseResult<List<TbContent>> selectAll() {
        return new ResponseResult<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), tbContentService.selectAll());
    }

    /**
     * 获取资源详情
     *
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    public ResponseResult<TbContent> getById(@PathVariable Long id) {
        return new ResponseResult<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), tbContentService.getById(id));
    }

    /**
     * 新增资源
     *
     * @param tbContent
     * @return
     */
    @PostMapping("/insert")
    public ResponseResult<Integer> insert(@RequestBody TbContent tbContent) {
        int count = tbContentService.insert(tbContent);

        if (count > 0) {
            return new ResponseResult<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), count);
        } else {
            return new ResponseResult<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString());
        }
    }

    /**
     * 更新资源
     *
     * @param tbContent
     * @return
     */
    @PutMapping("/update")
    public ResponseResult<Integer> update(@RequestBody TbContent tbContent) {
        int count = tbContentService.update(tbContent);

        if (count > 0) {
            return new ResponseResult<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), count);
        } else {
            return new ResponseResult<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString());
        }
    }

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult<Integer> delete(@PathVariable Long id) {
        int count = tbContentService.delete(id);

        if (count > 0) {
            return new ResponseResult<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), count);
        } else {
            return new ResponseResult<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString());
        }
    }
}
