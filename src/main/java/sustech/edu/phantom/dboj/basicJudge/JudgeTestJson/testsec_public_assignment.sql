INSERT INTO public.assignment (id, title, description, start_time, end_time, status, full_score, sample_database_path, valid) VALUES (1, 'title1', '# Computer Network hw1

**Name**:黎诗龙

**SID:**11811407

## 1

### packet switch

The transmission delay
$$
d_t =\frac{x}{p}\cdot \frac{p}{b} = \frac{x}{b}
$$
The store and forward delay
$$
d_{sf} = (k-1)\cdot \frac{p}{b}
$$
The propagation delay
$$
d_p = kd
$$
The total delay is 
$$
d_{pw} = d_t+d_{sf}+d_p = \frac{x}{b}+kd+(k-1)\cdot \frac{p}{b}
$$

### circuit switch

The transmission delay
$$
d_t =\frac{x}{b}
$$
The propagation delay
$$
d_p = kd
$$
The setup delay is $s$.

The total delay is 
$$
d_{cs} = \frac{x}{b}+kd+s
$$

If packet switch is smaller:
$$
d_p<d_c\\ \text{i.e.}
\\
\frac{x}{b}+kd+(k-1)\cdot \frac{p}{b}<\frac{x}{b}+kd+s
\\\implies s>\frac{p(k-1)}{b}
$$

## 2

Since in the description it says that *The handshaking process costs 2RTT before transmitting the file.*, we don''t consider transmitting file during the handshaking.

In my solution, I consider `Mb` as `10**6 bits`.

And in my solution, `0.5RTT` means in the final transmission, I just send the file, regardless of the return message from the receiver.  ', 1605546219888, 1605546251315, 'Running', 100, '/home/ooad/dboj/a1/s1.sql', true);