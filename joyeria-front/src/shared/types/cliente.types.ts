export interface Cliente {
  id: number;
  nombre: string;
  telefono: string;
  email: string;
  documento: string;
  activo: boolean;
}

export interface CreateClienteDto {
  nombre: string;
  telefono: string;
  email: string;
  documento: string;
}

export interface UpdateClienteDto extends Partial<CreateClienteDto> {}
