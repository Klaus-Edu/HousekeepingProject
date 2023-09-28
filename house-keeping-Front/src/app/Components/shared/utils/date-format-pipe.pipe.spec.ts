import { DateFormatPipe } from './date-format-pipe';

describe('DateFormatPipePipe', () => {
  it('create an instance', () => {
    const pipe = new DateFormatPipe('pt-BR');
    expect(pipe).toBeTruthy();
  });
});
