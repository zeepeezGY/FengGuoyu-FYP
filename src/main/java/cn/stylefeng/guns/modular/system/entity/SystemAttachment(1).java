package cn.stylefeng.guns.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 附件管理表
 * +----------------------------------------------------------------------
 * | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
 * +----------------------------------------------------------------------
 * | Copyright (c) 2016~2022 https://www.crmeb.com All rights reserved.
 * +----------------------------------------------------------------------
 * | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
 * +----------------------------------------------------------------------
 * | Author: CRMEB Team <admin@crmeb.com>
 * +----------------------------------------------------------------------
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_attachment")
@ApiModel(value="SystemAttachment对象", description="附件管理表")
public class SystemAttachment implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "att_id", type = IdType.AUTO)
    private Integer attId;

    @ApiModelProperty(value = "附件名称")
    private String name;

    @ApiModelProperty(value = "附件路径")
    private String attDir;

    @ApiModelProperty(value = "压缩图片路径")
    private String sattDir;

    @ApiModelProperty(value = "附件大小")
    private String attSize;

    @ApiModelProperty(value = "附件类型")
    private String attType;

    @ApiModelProperty(value = "图片上传类型 1本地 2七牛云 3OSS 4COS ")
    private Integer imageType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private String contractAddress;

    private String userId;

    private Integer status;

    private String hash;

}
