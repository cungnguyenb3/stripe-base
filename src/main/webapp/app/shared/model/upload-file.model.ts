export interface IUploadFile {
  id?: number;
  name?: string;
  fileName?: string;
  file?: any;
  imgUrl?: any;
  fileOnServer?: string;
  relativePath?: string;
}

export class UploadFile implements IUploadFile {
  constructor(
    public id?: number,
    public name?: string,
    public fileName?: string,
    public file?: any,
    public imgUrl?: any,
    public fileOnServer?: string,
    public relativePath?: string
  ) {}
}
