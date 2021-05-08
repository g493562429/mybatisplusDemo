package ${package.Controller};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

<#--
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
-->
/**
 * ${table.comment!} 前端控制器
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@Api(tags = {"${table.comment!}"})
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if><#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@Slf4j
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??>:${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>public class ${table.controllerName} extends ${superControllerClass}{
<#else>public class ${table.controllerName} {
</#if>

    @Resource
    private ${table.serviceName} ${(table.serviceName?substring(1))?uncap_first};

    @ApiOperation(value = "新增${table.comment!}")
    @PostMapping("/add")
    public int add(@RequestBody ${entity} ${entity?uncap_first}){
        return ${(table.serviceName?substring(1))?uncap_first}.add(${entity?uncap_first});
    }

    @ApiOperation(value = "删除${table.comment!}")
    @PostMapping("/delete")
    public int delete(Long id){
        return ${(table.serviceName?substring(1))?uncap_first}.delete(id);
    }

    @ApiOperation(value = "更新${table.comment!}")
    @PostMapping("/update")
    public int update(@RequestBody ${entity} ${entity?uncap_first}){
        return ${(table.serviceName?substring(1))?uncap_first}.updateData(${entity?uncap_first});
    }

    @ApiOperation(value = "查询${table.comment!}分页数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "页码"),
        @ApiImplicitParam(name = "pageSize", value = "每页条数")
    })
    @GetMapping("/get")
    public IPage<${entity}> get(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return ${(table.serviceName?substring(1))?uncap_first}.findListByPage(pageNum, pageSize);
    }

    @ApiOperation(value = "id查询${table.comment!}")
    @GetMapping("getById")
    public ${entity} getById(Long id){
        return ${(table.serviceName?substring(1))?uncap_first}.findById(id);
    }

}
</#if>