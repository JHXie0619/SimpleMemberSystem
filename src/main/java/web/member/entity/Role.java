package web.member.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import core.pojo.Core;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Core {
	private static final long serialVersionUID = 2791328122275752602L;
	@Id
	private Integer id;
	private String name;
}
