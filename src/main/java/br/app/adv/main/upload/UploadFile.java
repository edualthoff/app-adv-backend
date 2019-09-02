package br.app.adv.main.upload;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "upload_file")
@JsonIgnoreProperties(value = {"dataSession"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class UploadFile implements Serializable{

	private static final long serialVersionUID = -5671502251030948745L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="upload_id")
	private long id;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name="data_file")
	private byte[] data;
	
	@Column(name="tamanho")
	private long tamanho;
	
	@Column(name="data_created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date dataCreated;
	
	public UploadFile(String fileName, String tipo, byte[] data) {
		this.fileName = fileName;
		this.tipo = tipo;
		this.data = data;
		this.tamanho = data.length;
	}
	
	public UploadFile() {
		
	}
	public long getId() {
		return id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public Date getDataCreated() {
		return dataCreated;
	}
	public long getTamanho() {
		return tamanho;
	}	
}
