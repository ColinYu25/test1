CREATE TABLE `ls_aj_sx_qtcfxx` (
  `UNID` varchar(24) NOT NULL COMMENT '唯一标识',
  `XM` varchar(50) NOT NULL COMMENT '被处罚对象姓名',
  `SFZH` varchar(18) NOT NULL COMMENT '被处罚对象居民身份证号',
  `JDWSH` varchar(200) default NULL COMMENT '处罚决定书文号',
  `CFSY` varchar(2000) default NULL COMMENT '处罚事由',
  `CFYJ` varchar(2000) default NULL COMMENT '处罚依据',
  `CFNR` varchar(2000) default NULL COMMENT '处罚内容',
  `CFRQ` varchar(50) default NULL COMMENT '作出处罚决定的日期',
  `JLZT` char(1) default NULL COMMENT '记录状态',
  `RKRQ` datetime default NULL COMMENT '入库日期',
  `CLZT` char(1) default NULL COMMENT '处理状态',
  PRIMARY KEY  (`UNID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产安全领域对自然人的其它处罚信息';


CREATE TABLE `ls_aj_sx_scsgcfxx` (
  `UNID` varchar(24) NOT NULL COMMENT '唯一标识',
  `XM` varchar(50) NOT NULL COMMENT '被处罚对象姓名',
  `SFZH` varchar(18) NOT NULL COMMENT '被处罚对象居民身份证号',
  `JDWSH` varchar(200) default NULL COMMENT '处罚决定书文号',
  `CFSY` varchar(2000) default NULL COMMENT '处罚事由',
  `CFYJ` varchar(2000) default NULL COMMENT '处罚依据',
  `CFNR` varchar(2000) default NULL COMMENT '处罚内容',
  `CFRQ` varchar(50) default NULL COMMENT '作出处罚决定的日期',
  `JLZT` char(1) default NULL COMMENT '记录状态',
  `RKRQ` datetime default NULL COMMENT '入库日期',
  `CLZT` char(1) default NULL COMMENT '处理状态',
  PRIMARY KEY  (`UNID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对生产安全事故主要责任人的处罚信息';


CREATE TABLE `ls_aj_sx_tzzyrycfxx` (
  `UNID` varchar(24) NOT NULL COMMENT '唯一标识',
  `XM` varchar(50) NOT NULL COMMENT '被处罚对象姓名',
  `SFZH` varchar(18) NOT NULL COMMENT '被处罚对象居民身份证号',
  `JDWSH` varchar(200) default NULL COMMENT '处罚决定书文号',
  `CFSY` varchar(2000) default NULL COMMENT '处罚事由',
  `CFYJ` varchar(2000) default NULL COMMENT '处罚依据',
  `CFNR` varchar(2000) default NULL COMMENT '处罚内容',
  `CFRQ` varchar(50) default NULL COMMENT '作出处罚决定的日期',
  `JLZT` char(1) default NULL COMMENT '记录状态',
  `RKRQ` datetime default NULL COMMENT '入库日期',
  `CLZT` char(1) default NULL COMMENT '处理状态',
  PRIMARY KEY  (`UNID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='特种作业人员处罚信息';

CREATE TABLE `ls_aj_sx_aqpjscfxx` (
  `UNID` varchar(24) NOT NULL COMMENT '唯一标识',
  `XM` varchar(50) NOT NULL COMMENT '被处罚对象姓名',
  `SFZH` varchar(18) NOT NULL COMMENT '被处罚对象居民身份证号',
  `JDWSH` varchar(200) default NULL COMMENT '处罚决定书文号',
  `CFSY` varchar(2000) default NULL COMMENT '处罚事由',
  `CFYJ` varchar(2000) default NULL COMMENT '处罚依据',
  `CFNR` varchar(2000) default NULL COMMENT '处罚内容',
  `CFRQ` varchar(50) default NULL COMMENT '作出处罚决定的日期',
  `JLZT` char(1) default NULL COMMENT '记录状态',
  `RKRQ` datetime default NULL COMMENT '入库日期',
  `CLZT` char(1) default NULL COMMENT '处理状态',
  PRIMARY KEY  (`UNID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安全评价师处罚信息';

CREATE TABLE `ls_aj_sx_aqgcscfxx` (
  `UNID` varchar(24) NOT NULL COMMENT '唯一标识',
  `XM` varchar(50) NOT NULL COMMENT '被处罚对象姓名',
  `SFZH` varchar(18) NOT NULL COMMENT '被处罚对象居民身份证号',
  `JDWSH` varchar(200) default NULL COMMENT '处罚决定书文号',
  `CFSY` varchar(2000) default NULL COMMENT '处罚事由',
  `CFYJ` varchar(2000) default NULL COMMENT '处罚依据',
  `CFNR` varchar(2000) default NULL COMMENT '处罚内容',
  `CFRQ` varchar(50) default NULL COMMENT '作出处罚决定的日期',
  `JLZT` char(1) default NULL COMMENT '记录状态',
  `RKRQ` datetime default NULL COMMENT '入库日期',
  `CLZT` char(1) default NULL COMMENT '处理状态',
  PRIMARY KEY  (`UNID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安全工程师处罚信息';




CREATE TABLE `zj_xzcf_jgxx` (
  `UNID` varchar(50) NOT NULL COMMENT '唯一标识',
  `AREACODE` varchar(10) default NULL COMMENT '所属地区行政区划编码',
  `XZCF_REGINNAME` varchar(50) default NULL COMMENT '所属地区名称',
  `XZCF_ORGNAME` varchar(100) default NULL COMMENT '执法部门名称',
  `XZCF_ORGCODE` varchar(30) default NULL COMMENT '执法部门组织编码',
  `XZCFWS_CODE` varchar(100) default NULL COMMENT '行政处罚决定书文号',
  `XZCFWS_NAME` varchar(2000) default NULL COMMENT '案件名称',
  `BXZCF_TYPE` int(1) default NULL COMMENT '被处罚对象类别',
  `BXZCF_NAME` varchar(200) default NULL COMMENT '被处罚对象名称',
  `APPLY_CARDTYPE` varchar(30) default NULL COMMENT '被处罚对象证件类型',
  `BXZCF_CARDNUMBER` varchar(100) default NULL COMMENT '被处罚对象证件号码',
  `BXZCF_LEGALMAN` varchar(30) default NULL COMMENT '被处罚单位法定代表人姓名',
  `BXZCF_LEGALMANCARDNUMBER` varchar(18) default NULL COMMENT '被处罚单位法定代表人身份证号码',
  `XZCF_NAME` varchar(2000) default NULL COMMENT '行政处罚权力事项名称',
  `XZCF_CODE` varchar(50) default NULL COMMENT '权力内部编码（权力事项唯一码',
  `XZCF_ZY` text COMMENT '对外公开的行政处罚决定书全文或摘要',
  `XZCF_DATE` varchar(20) default NULL COMMENT '作出行政处罚的日期',
  `XZCF_GKLX` int(1) default NULL COMMENT '是否公开',
  `XZCF_BGKYJ` varchar(1000) default NULL COMMENT '不公开依据',
  `XZCF_STATE` int(1) default NULL COMMENT '状态标记',
  `XZCF_CANCEL` varchar(1000) default NULL COMMENT '撤销处罚的原因说明',
  `ORDERTIME` varchar(20) default NULL COMMENT '排序时间',
  `XZCF_DATAVERSION` int(38) default NULL COMMENT '信息版本号',
  PRIMARY KEY  (`UNID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省行政处罚结果信息';