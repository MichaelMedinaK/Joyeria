export interface Revendedor {
  id: number;
  nombre: string;
  telefono: string;
  email: string;
  documento: string;
  activo: boolean;
}

export interface CreateRevendedorDto {
  nombre: string;
  telefono: string;
  email: string;
  documento: string;
}

export interface UpdateRevendedorDto extends Partial<CreateRevendedorDto> {}
