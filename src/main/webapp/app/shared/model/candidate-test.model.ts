export interface ICandidateTestModel {
  interviewLetterId?: number;
  startTime?: Date;
  endTime?: Date;
  bufferTime?: string;
  responseFile?: string;
  token?: string;
  expiryDate?: Date;
  status?: string;
  candidateId?: string;
  quizId?: string;
  duration?: string;
  positionName?: string;
  candidatesEmail?: string;
  nowTime?: Date;
}

export class CandidateTestModel implements ICandidateTestModel {
  constructor(
    public interviewLetterId?: number,
    public startTime?: Date,
    public endTime?: Date,
    public bufferTime?: string,
    public responseFile?: string,
    public token?: string,
    public expiryDate?: Date,
    public status?: string,
    public candidateId?: string,
    public quizId?: string,
    public duration?: string,
    public positionName?: string,
    public candidatesEmail?: string,
    public nowTime?: Date
  ) {}
}
