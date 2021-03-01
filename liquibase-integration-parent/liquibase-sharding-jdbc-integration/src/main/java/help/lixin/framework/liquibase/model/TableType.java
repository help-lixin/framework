package help.lixin.framework.liquibase.model;

public enum TableType {
    // 逻辑表(虚拟表)
    LogicalTable,
    // 物理表(真实表)
    PhysicalTable,
    // 广播表(所有Schema都存在的表,生产环境很少用,暂时留着)
    BroadcastTable
}
