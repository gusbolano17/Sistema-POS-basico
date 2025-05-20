export interface ResponseDto<T> {
  msg: string;
  code : string
  body: T
}
