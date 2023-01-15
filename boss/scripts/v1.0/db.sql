create table if not exists boss.t_operation_log
(
  c_id varchar(36) not null comment '主键' primary key,
  c_oper_id varchar(36) not null comment '操作人id',
  c_oper_name varchar(40) null comment '操作人姓名',
  c_oper_type tinyint not null comment '操作类型 0. 显示 1. 创建 2.更新 3. 删除',
  c_oper_address varchar(40) null comment '操作人ip地址',
  c_oper_time timestamp not null comment '操作时间',
  c_content varchar(255) null comment '操作内容',
  c_code varchar(40) null comment '操作标识',
  c_input_data text null comment '输入数据',
  c_output_data text null comment '输出数据',
  c_create_time timestamp null comment '创建时间'
) comment '操作日志表';