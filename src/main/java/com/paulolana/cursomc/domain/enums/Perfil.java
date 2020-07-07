package com.paulolana.cursomc.domain.enums;

public enum Perfil {
	
	//Os nomes ROLER_ADIM e ROLER_CLIENTE são exigências do framework Sprintboot Security

	ADMIN(1, "ROLER_ADMIN"), 
	CLIENTE(2, "ROLER_CLIENTE");

	private int codigo;
	private String descricao;

	private Perfil(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		for (Perfil x : Perfil.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id Inválido: " + codigo);
	}
}
