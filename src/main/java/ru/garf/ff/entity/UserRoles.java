package ru.garf.ff.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "user_roles")
public class UserRoles {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_roles_id_seq")
	@SequenceGenerator(name="user_roles_id_seq", sequenceName = "user_roles_id_seq")
	private Long id;
	@Column(name = "user_id")
	private Long userid;
	@Column(name = "role_id")
	private Long roleid;

	
	public UserRoles() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	 public UserRoles(Long userid, Long roleid) {
		super();
		this.userid = userid;
		this.roleid = roleid;
	}

	public Long getId() {
	 return id;
	 }

	public void setId(Long id) {
		this.id = id;
	}

	 public Long getUserid() {
	 return userid;
	 }

	public void setUserid(Long user_id) {
		this.userid = user_id;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long role_id) {
		this.roleid = role_id;
	}


}
