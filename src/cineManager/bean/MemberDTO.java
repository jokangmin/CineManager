package cineManager.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor 
@Getter 
@Setter

public class MemberDTO {
	
	@NonNull 
	private String name; // 이름
	@NonNull
	private String id; // id
	@NonNull
	private String pwd; // password
	@NonNull
	private String phone; // 전화번호
	
//	// 생성자
//    public MemberDTO(String name, String id, String pwd, String phone) {
//        this.name = name;
//        this.id = id;
//        this.pwd = pwd;
//        this.phone = phone;
//    }
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getPwd() {
//		return pwd;
//	}
//
//	public void setPwd(String pwd) {
//		this.pwd = pwd;
//	}
//
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}

	@Override
	public String toString() {
		return name + "\t" + id + "\t" + pwd + "\t" + phone;
	}
}